/*
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

import React, { Component } from 'react';
import Timeline from 'react-visjs-timeline'
import Snapshot from './Snapshot'
import Logs from './Logs'
import BackupLoader from './BackupLoader'
import { Grid, Row, Col } from 'react-bootstrap';

import './App.css';
import 'bootstrap/dist/css/bootstrap.css'

const options = {
    width: '100%',
    height: '150px',
    showMajorLabels: true,
    showCurrentTime: true,
    zoomMin: 100000,
    selectable:true,
    type: 'background',
    format: {
        minorLabels: {
            minute: 'h:mma',
            hour: 'ha'
        }
    }
};

function timeToDate(timestamp){
    return new Date(timestamp*1000);
}

function getItems(){
    var xhr = new XMLHttpRequest();
    var url = "http://localhost:8080/database";
    xhr.open('GET', url, true);

    xhr.responseType = 'json';

    xhr.send();

    xhr.onreadystatechange = processRequest;

    function processRequest(e) {
        if (xhr.readyState === 4 && xhr.status === 200) {
            return xhr.response
        }
    }

    return [
        {
            id: 1,
            start: timeToDate(1496321093),
            content: 'Major Snapshot',
            type: 'box'
        },
        {
            id: 2,
            start: timeToDate(1496324093),
            content: 'Major Snapshot 2',
            type: 'box'
        },
    ]
}

function onSelect(event){
    console.log("Selected nodes:");
    console.log(event.items);
}

class App extends Component {
    render() {
        return (
            <div className="App">
                <div className="App-header">
                    <h2>Backup Gui</h2>
                </div>

                <div className="App-content">
                    <Timeline options={options}
                              items={getItems()}
                              selectHandler={onSelect}
                    />
                    <div className="content">
                        <Grid>
                            <Row>
                                <Col xs={3} md={3}>
                                    <Snapshot list={getItems()} />
                                </Col>
                                <Col xs={5} md={5} className="mainContent">
                                    <BackupLoader />
                                </Col>
                                <Col xs={4} md={4} className="BackupList">
                                    <Logs />
                                </Col>
                            </Row>
                        </Grid>
                    </div>
                </div>
            </div>
        );
    }
}

export default App;
