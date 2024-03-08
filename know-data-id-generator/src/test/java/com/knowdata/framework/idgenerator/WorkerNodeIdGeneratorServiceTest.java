package com.knowdata.framework.idgenerator;

import com.knowdata.framework.idgenerator.service.WorkerNodeIdGenerator;
import org.junit.Test;

public class WorkerNodeIdGeneratorServiceTest {
    // ...
    //375555673007587346
    //375555673007587347
    //375555673007587348
    // ...
    @Test
    public void uidGenerateTest(){
        WorkerNodeIdGenerator idWorker = new WorkerNodeIdGenerator(0, 0);
        for (int i = 0; i < 10; i++) {
            long id = idWorker.nextId();
            System.out.println(id);
        }
    }
}
