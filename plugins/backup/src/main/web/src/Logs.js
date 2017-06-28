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
import {Treebeard, decorators} from 'react-treebeard';

import './Logs.css';

function buildTree(parts,treeNode) {
    if(parts.length === 0 || parts[0].length <= 1)
    {
        return;
    }
    for(var i = 0 ; i < treeNode.length; i++)
    {
        if(parts[0] === treeNode[i].name)
        {
            buildTree(parts.splice(1,parts.length),treeNode[i].children);
            return;
        }
    }
    var newNode = {'name': parts[0] ,'children':[]};
    treeNode.push(newNode);
    buildTree(parts.splice(1,parts.length),newNode.children);
}

decorators.Header = (props) => {
    const style = props.style;
    const iconType = props.node.children ? 'folder' : 'file-text';
    const iconClass = `fa fa-${iconType}`;
    const iconStyle = { marginRight: '5px' };
    return (
        <div style={style.base}>
            <div style={style.title}>
                <i className={iconClass} style={iconStyle}/>
                {props.node.name}
            </div>
        </div>
    );
};

export default class Logs extends Component{
    constructor(props){
        super(props);
        this.state = {logData: []};
        this.onToggle = this.onToggle.bind(this);
    }

    onToggle(node, toggled){
        if(this.state.cursor){this.state.cursor.active = false;}
        node.active = true;
        if(node.children){ node.toggled = toggled; }
        this.setState({ cursor: node });
    }

    componentDidMount(){
        var xhr = new XMLHttpRequest();
        xhr.open('GET', "http://localhost:8080/logs", true);
        xhr.send();

        xhr.onreadystatechange = processRequest.bind(this);

        function processRequest(e) {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var tree = [];
                var elems = JSON.parse(e.target.response);

                for(var i = 0; i < elems.length; i++){
                    buildTree(elems[i].split("/"), tree)
                }

                console.log(tree);
                this.setState({logData: tree})
            }
        }
    }

    render(){
        return (
            <div className="BackupList component">
                <h1>Log Structure </h1>
                <br />
                <Treebeard
                    data={this.state.logData}
                    onToggle={this.onToggle}
                    decorators={decorators}
                />
            </div>
        );
    }
}