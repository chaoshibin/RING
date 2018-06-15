package com.ring.core.util;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 该方法会出现惊群效应
 * 改进之后见类：ImproveLock
 * @author maowenwu
 *
 */
public class DistributeLock implements Lock {
	private static Logger logger = LoggerFactory.getLogger(DistributeLock.class);

	private static final String ZK_IP_PORT = "localhost:2181";
	private static final String LOCK_NODE = "/lock";

	private ZkClient client = new ZkClient(ZK_IP_PORT);

	private CountDownLatch cdl = null;

	// 实现阻塞式的加锁
	@Override
	public void lock() {
		if (tryLock()) {
			return;
		}
		waitForLock();
		lock();
	}

	// 阻塞时的实现
	private void waitForLock() {
		// 给节点加监听
		IZkDataListener listener = new IZkDataListener() {
			@Override
			public void handleDataDeleted(String dataPath) throws Exception {
				logger.info("-------get data delete event---------------");
				if (cdl != null) {
					cdl.countDown();
				}
			}

			@Override
			public void handleDataChange(String dataPath, Object data) throws Exception {
			}
		};

		client.subscribeDataChanges(LOCK_NODE, listener);
		if (client.exists(LOCK_NODE)) {
			try {
				cdl = new CountDownLatch(1);
				cdl.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		client.unsubscribeDataChanges(LOCK_NODE, listener);
	}

	// 实现非阻塞式的加锁
	@Override
	public boolean tryLock() {
		try {
			client.createPersistent(LOCK_NODE);
			return true;
		} catch (ZkNodeExistsException e) {
			return false;
		}
	}

	@Override
	public void unlock() {
		client.delete(LOCK_NODE);
	}

	// -------------------------------

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		return false;
	}

	@Override
	public Condition newCondition() {
		return null;
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
	}
}
