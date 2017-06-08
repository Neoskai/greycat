import React, { Component } from 'react';
import Timeline from 'react-visjs-timeline'
import Snapshot from './Snapshot'
import BackupList from './BackupList'
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
                                <Col xs={6} md={6} className="mainContent">
                                    <BackupLoader />
                                </Col>
                                <Col xs={3} md={3} className="BackupList">
                                    <BackupList />
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
