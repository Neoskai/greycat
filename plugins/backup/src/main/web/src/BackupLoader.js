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
import { Button, Modal, Tabs, Tab  } from 'react-bootstrap';

import './Snapshot.css';


export default class BackupLoader extends Component {
    constructor(props){
        super(props);

        this.state = {
            showModal: false,
            nodes: "",
            startStamp: 0,
            endStamp: 99999999999999,

            fromNodes: "",
            startDate: new Date().toJSON().slice(0,19)
        }

        this.close = this.close.bind(this);
        this.open = this.open.bind(this);
        this.launchBackup = this.launchBackup.bind(this);
        this.launchPartial = this.launchPartial.bind(this);
        this.launchFrom = this.launchFrom.bind(this);

        this.handleInputChange = this.handleInputChange.bind(this);
    }

    close() {
        this.setState({ showModal: false });
    }

    open() {
        this.setState({ showModal: true });
    }

    launchFrom(event){
        event.preventDefault();
        var nodes = this.state.nodes;
        var startDate = this.state.startDate;

        var str = startDate.valueOf();
        var date = new Date(str);

        var url = "http://localhost:8080/backup/partialNode";
        var nodeQuery = "nodes=" + nodes;
        var startQuery= "start=" + date.getTime();
        var endQuery = "end=" + (new Date()).getTime();

        var finalQuery = url + "?" + nodeQuery + "&" + startQuery + "&" + endQuery;

        var xhr = new XMLHttpRequest();
        xhr.open('GET', finalQuery, true);

        xhr.setRequestHeader('Access-Control-Allow-Headers', '*');
        xhr.setRequestHeader('Content-type', 'application/ecmascript');
        xhr.setRequestHeader('Access-Control-Allow-Origin', '*');

        xhr.send();

        xhr.onreadystatechange = processRequest;

        function processRequest(e) {
            if (xhr.readyState === 4 && xhr.status === 200) {
                console.log("Backup done");
            }
        }
    }

    launchPartial(event){
        var nodes = this.state.nodes;
        var startStamp = this.state.startStamp;
        var endStamp = this.state.endStamp;

        var url = "http://localhost:8080/backup/partialNode";
        var nodeQuery = "nodes=" + nodes;
        var startQuery= "start=" + startStamp;
        var endQuery = "end=" + endStamp;

        var finalQuery = url + "?" + nodeQuery + "&" + startQuery + "&" + endQuery;

        var xhr = new XMLHttpRequest();
        xhr.open('GET', finalQuery, true);

        xhr.setRequestHeader('Access-Control-Allow-Headers', '*');
        xhr.setRequestHeader('Content-type', 'application/ecmascript');
        xhr.setRequestHeader('Access-Control-Allow-Origin', '*');

        xhr.send();

        xhr.onreadystatechange = processRequest;

        function processRequest(e) {
            if (xhr.readyState === 4 && xhr.status === 200) {
                console.log("Backup done");
            }
        }
    }

    launchBackup(){
        var xhr = new XMLHttpRequest();
        xhr.open('GET', "http://localhost:8080/backup/full", true);

        xhr.setRequestHeader('Access-Control-Allow-Headers', '*');
        xhr.setRequestHeader('Content-type', 'application/ecmascript');
        xhr.setRequestHeader('Access-Control-Allow-Origin', '*');

        xhr.send();

        xhr.onreadystatechange = processRequest;

        function processRequest(e) {
            if (xhr.readyState === 4 && xhr.status === 200) {
                console.log("Backup done");
            }
        }

        this.setState({ showModal: false });
    }

    handleInputChange(event) {
        const target = event.target;
        const value = target.type === 'checkbox' ? target.checked : target.value;
        const name = target.name;

        this.setState({
            [name]: value
        });
    }

    render() {
        return (
            <div>
                <Tabs defaultActiveKey={2} id="uncontrolled-tab-example">
                    <Tab eventKey={1} title="Complete">
                        <h1>Total Recovery</h1>
                        <br />
                        <p>
                            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean mauris nibh, accumsan nec mollis eu, varius et ligula. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Donec eget cursus magna. Nam malesuada, nulla id gravida malesuada, sem mi porta lorem, vitae vehicula tortor velit a lectus. Vivamus interdum dictum risus, eget vehicula eros consequat ut. Vestibulum ac finibus risus, non malesuada urna. Phasellus commodo tempus lectus, sit amet rhoncus tellus vestibulum at. Maecenas porttitor faucibus sodales. Sed eget hendrerit sem, a scelerisque elit.
                        </p>
                        <br />
                        <Button bsStyle="danger" onClick={this.open}>Launch Full Backup</Button>

                        <Modal show={this.state.showModal} onHide={this.close}>
                            <Modal.Header closeButton>
                                <Modal.Title>Confirm Backup</Modal.Title>
                            </Modal.Header>
                            <Modal.Body>
                                <h4>Are you sure you want to launch a full recovery?</h4>
                            </Modal.Body>
                            <Modal.Footer>
                                <Button bsStyle="danger" onClick={this.close}>No</Button>
                                <Button bsStyle="success" onClick={this.launchBackup}>Yes</Button>
                            </Modal.Footer>
                        </Modal>
                    </Tab>

                    <Tab eventKey={2} title="Partial">
                        <h1>Partial Recovery</h1>
                        <br />
                        <form onSubmit={this.launchPartial}>
                            <label>
                                Nodes: <input type="text" name="nodes" onChange={this.handleInputChange} value={this.state.nodes} />
                            </label>
                            <br />
                            <label>
                                Start Stamp: <input type="number" name="startStamp" onChange={this.handleInputChange} value={this.state.startStamp}/>
                            </label>
                            <br />
                            <label>
                                End Stamp: <input type="number" name="endStamp" onChange={this.handleInputChange} value={this.state.endStamp}/>
                            </label>
                            <br /> <br />
                            <Button type="submit" bsStyle="danger">Launch Recovery</Button>
                        </form>
                    </Tab>

                    <Tab eventKey={3} title="From Date">
                        <h1>Partial Recovery From Date</h1>
                        <br />
                        <form onSubmit={this.launchFrom}>
                            <label>
                                Nodes: <input type="text" name="nodes" value={this.state.fromNodes} onChange={this.handleInputChange} />
                            </label>
                            <br />
                            <label>
                                Start Date: <input type="datetime-local" step="1" name="startDate" value={this.state.startDate} onChange={this.handleInputChange} />
                            </label>
                            <br /> <br />
                            <Button  type="submit" bsStyle="danger">Launch Recovery</Button>
                        </form>
                    </Tab>
                </Tabs>


            </div>
        )
    }
}