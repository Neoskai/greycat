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

import './Snapshot.css';

export default class Snapshot extends Component{
    constructor(props){
        super(props);
        this.state = {snapshots: []};
    }

    componentDidMount(){
        var xhr = new XMLHttpRequest();
        xhr.open('GET', "http://localhost:8080/database", true);
        xhr.send();

        xhr.onreadystatechange = processRequest.bind(this);

        function processRequest(e) {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var elems = JSON.parse(e.target.response);

                this.setState({snapshots: elems})
            }
        }
    }


    render(){
        return (
            <div className="Snapshot list4">
                <h1>RocksDB </h1>
                <br />
                <ul>
                    {this.state.snapshots.map(function(listValue){
                        return <li><a href="#"><strong> {new Date(listValue.timestamp*1000).toLocaleString()} </strong> ID: {listValue.id}<br /></a></li>;
                    })}
                </ul>
            </div>
        )
    }
}