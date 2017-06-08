import React, { Component } from 'react';

import './Snapshot.css';

export default class Snapshot extends Component{
    render(){
        return (
            <div className="Snapshot list4">
                <h1>RocksDB </h1>
                <br />
                <ul>
                    {this.props.list.map(function(listValue){
                        return <li><a href="#"><strong> {listValue.start.toLocaleString()} </strong> ID: {listValue.id}<br /></a></li>;
                    })}
                </ul>
            </div>
        )
    }
}