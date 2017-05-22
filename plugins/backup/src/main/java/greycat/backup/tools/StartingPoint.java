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

package greycat.backup.tools;

public class StartingPoint {

    private Long filenumber;
    private Long startingEvent;

    public StartingPoint(Long filenumber, Long startingEvent){
        this.filenumber = filenumber;
        this.startingEvent = startingEvent;
    }

    public Long getFilenumber() {
        return filenumber;
    }

    public void setFilenumber(Long filenumber) {
        this.filenumber = filenumber;
    }

    public Long getStartingEvent() {
        return startingEvent;
    }

    public void setStartingEvent(Long startingEvent) {
        this.startingEvent = startingEvent;
    }

    @Override
    public String toString() {
        return "StartingPoint{" +
                "filenumber=" + filenumber +
                ", startingEvent=" + startingEvent +
                '}';
    }
}