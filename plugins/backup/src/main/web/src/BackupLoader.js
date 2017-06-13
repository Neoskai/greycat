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

import React, { Component } from 'react';
import { Button, Modal, Tabs, Tab  } from 'react-bootstrap';

import './Snapshot.css';


export default class BackupLoader extends Component {
    constructor(props){
        super(props);

        this.state = {
            showModal: false
        }

        this.close = this.close.bind(this);
        this.open = this.open.bind(this);
        this.launchBackup = this.launchBackup.bind(this);
    }

    close() {
        this.setState({ showModal: false });
    }

    open() {
        this.setState({ showModal: true });
    }

    launchBackup(){
        this.setState({ showModal: false });
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
                        <form>
                            <Button bsStyle="danger" onClick={this.open}>Launch Recovery</Button>
                        </form>
                    </Tab>
                </Tabs>


            </div>
        )
    }
}