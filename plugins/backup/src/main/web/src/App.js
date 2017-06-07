import React, { Component } from 'react';
import Timeline from 'react-visjs-timeline'

import './App.css';

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

var items = [
    {
        id: 1,
        start: timeToDate(1496321093),
        content: 'Major Snapshot',
        type: 'box'
    }];

function onSelect(event){
    console.log("Selected nodes:");
    console.log(event.items);
}

class App extends Component {
    constructor({initialGraph}) {
        super();
        this.state = {
            graph: initialGraph
        };
    }

    render() {
        return (
            <div className="App">
                <div className="App-header">
                    <h2>Backup Gui</h2>
                </div>
                <div className="App-content">
                    <Timeline options={options}
                              items={items}
                              selectHandler={onSelect}
                    />
                </div>
            </div>
        );
    }
}

export default App;
