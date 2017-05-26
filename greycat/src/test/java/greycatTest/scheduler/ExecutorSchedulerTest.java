/**
 * Copyright 2017 The GreyCat Authors.  All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package greycatTest.scheduler;

import greycat.Callback;
import greycat.DeferCounterSync;
import greycat.Graph;
import greycat.GraphBuilder;
import greycat.TaskResult;
import greycat.scheduler.ExecutorScheduler;

import static greycat.internal.task.CoreActions.print;
import static greycat.Tasks.newTask;

/**
 * @ignore ts
 */
public class ExecutorSchedulerTest {

    //@Test
    public void test() {
        Graph g = new GraphBuilder().withScheduler(new ExecutorScheduler()).build();
        DeferCounterSync waiter = g.newSyncCounter(1);
        g.connect(new Callback<Boolean>() {
            @Override
            public void on(Boolean result) {
                newTask().loopPar("0", "99", newTask().then(print("{{result}}")))
                        .execute(g, new Callback<TaskResult>() {
                            @Override
                            public void on(TaskResult result) {
                                System.out.println("end");
                                g.disconnect(new Callback<Boolean>() {
                                    @Override
                                    public void on(Boolean result) {
                                        System.out.println("Disconnected");
                                        waiter.count();
                                    }
                                });
                            }
                        });
            }
        });
        waiter.waitResult();
        System.out.println("Result are here...");

/*
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
*/
    }

}
