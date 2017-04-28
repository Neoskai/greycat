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

package greycat.nsq;

import greycat.Action;
import greycat.TaskContext;
import greycat.TaskHook;

public class NSQHook  implements TaskHook{
    private String _address;
    private int _port;

    private NSQSender _sender;

    public NSQHook(String address, int port){
        _address = address;
        _port = port;
    }

    @Override
    public void start(TaskContext initialContext) {
        System.out.println("Hook start");
        _sender = new NSQSender(_address, _port);
    }

    @Override
    public void beforeAction(Action action, TaskContext context) {
        System.out.println("Hook captured new Action: " + action.name());

    }

    @Override
    public void afterAction(Action action, TaskContext context) {
        System.out.println("After action hook");
        _sender.sendMessage(action.name());
    }

    @Override
    public void beforeTask(TaskContext parentContext, TaskContext context) {
        System.out.println("Before task hook");
    }

    @Override
    public void afterTask(TaskContext context) {
        System.out.println("After task hook");
    }

    @Override
    public void end(TaskContext finalContext) {
        System.out.println("Hook end");
    }
}
