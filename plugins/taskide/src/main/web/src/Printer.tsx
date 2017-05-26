import {Type} from 'greycat';
import * as React from 'react';

export class Printer extends React.Component<String,String> {

    sayHello() {
        console.log("hello", Type.BOOL, React);
    }

}