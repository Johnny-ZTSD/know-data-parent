package com.knowdata.framework.study.rocksdb.entry;

import org.junit.Test;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;

/**
 * Hello world!
 *
 */
public class RocksDBTest {
    RocksDB rocksDB;

    String path = "C:\\Users\\johnny\\Desktop\\rocksdb";

    /**
     * @reference-doc
     *  [1] [数据库] 嵌入式数据库RockdsDB 草稿  - 博客园/千千寰宇 - https://www.cnblogs.com/johnnyzen/p/18210497
     * @throws RocksDBException
     */
    @Test
    public void testOpen() throws RocksDBException {
        RocksDB.loadLibrary();
        Options options = new Options();
        options.setCreateIfMissing(true);//如果不存在该库时，创建之

        rocksDB = RocksDB.open(options, path);

        //write
        rocksDB.put("key".getBytes(), "val".getBytes());

        //read
        byte[] bytes = rocksDB.get("key".getBytes());

        System.out.println(new String(bytes));//"val"
    }
}
