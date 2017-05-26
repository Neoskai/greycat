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
import MonacoEditor from 'react-monaco-editor';
import React from 'react';

class TaskEditor extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            code: 'readGlobalIndex(nodes)\n.forEach({\n\tprintln("{{result}}")\n})\n',
            actions: []
        };
        global.context.code = this.state.code;
    }

    editorWillMount(monaco) {
        monaco.languages.register({id: 'mwgTask'});
        monaco.languages.registerCompletionItemProvider('mwgTask', {
            provideCompletionItems: function (model, position) {
                let textUntilPosition = model.getValueInRange({
                    startLineNumber: 1,
                    startColumn: 1,
                    endLineNumber: position.lineNumber,
                    endColumn: position.column
                }).trim();
                if (textUntilPosition.endsWith(".")) {
                    let result = [];
                    let registry = global.context.actions;
                    for (var i = 0; i < registry.length; i++) {
                        let declaration = registry[i];
                        let params = declaration['params'];
                        var insertParam = '(';
                        if (params !== undefined && params != null) {
                            for (var j = 0; j < params.length; j++) {
                                if (j !== 0) {
                                    insertParam += ',';
                                }
                                switch (params[j]) {
                                    case "STRING":
                                        insertParam += "\"param\"";
                                        break;
                                    case "LONG":
                                        insertParam += "1";
                                        break;
                                    case "INT":
                                        insertParam += "1";
                                        break;
                                    case "DOUBLE":
                                        insertParam += "1.0";
                                        break;
                                    case "TASK":
                                        insertParam += "{}";
                                        break;
                                    case "TASK_ARRAY":
                                        insertParam += "{},{}";
                                        break;
                                    case "STRING_ARRAY":
                                        insertParam += "\"varargs\",\"varargs2\"";
                                        break;
                                    case "DOUBLE_ARRAY":
                                        insertParam += "1.0, 2.0";
                                        break;
                                    case "INT_ARRAY":
                                        insertParam += "1, 2";
                                        break;
                                    case "LONG_ARRAY":
                                        insertParam += "1, 2";
                                        break;
                                    default:
                                        insertParam += "\"param\"";
                                        break;
                                }
                            }
                        }
                        insertParam += ')';
                        result.push({
                            label: declaration['name'],
                            kind: monaco.languages.CompletionItemKind.Function,
                            documentation: declaration['description'],
                            insertText: declaration['name'] + insertParam
                        });
                    }
                    return result;
                } else {
                    return [];
                }
            }
        });
        monaco.languages.setMonarchTokensProvider('mwgTask', {
            // Set defaultToken to invalid to see what you do not tokenize yet
            // defaultToken: 'invalid',
            keywords: [
                'abstract', 'continue', 'for', 'new', 'switch', 'assert', 'goto', 'do',
                'if', 'private', 'this', 'break', 'protected', 'throw', 'else', 'public',
                'enum', 'return', 'catch', 'try', 'interface', 'static', 'class',
                'finally', 'const', 'super', 'while', 'true', 'false'
            ],

            typeKeywords: [
                'boolean', 'double', 'byte', 'int', 'short', 'char', 'void', 'long', 'float'
            ],

            operators: [
                '=', '>', '<', '!', '~', '?', ':', '==', '<=', '>=', '!=',
                '&&', '||', '++', '--', '+', '-', '*', '/', '&', '|', '^', '%',
                '<<', '>>', '>>>', '+=', '-=', '*=', '/=', '&=', '|=', '^=',
                '%=', '<<=', '>>=', '>>>='
            ],

            // we include these common regular expressions
            symbols: /[=><!~?:&|+\-*\/\^%]+/,

            // C# style strings
            escapes: /\\(?:[abfnrtv\\"']|x[0-9A-Fa-f]{1,4}|u[0-9A-Fa-f]{4}|U[0-9A-Fa-f]{8})/,

            // The main tokenizer for our languages
            tokenizer: {
                root: [
                    // identifiers and keywords
                    [/[a-z_$][\w$]*/, {
                        cases: {
                            '@typeKeywords': 'keyword',
                            '@keywords': 'keyword',
                            '@default': 'identifier'
                        }
                    }],
                    [/[A-Z][\w\$]*/, 'type.identifier'],  // to show class names nicely

                    // whitespace
                    {include: '@whitespace'},

                    // delimiters and operators
                    [/[{}()\[\]]/, '@brackets'],
                    [/[<>](?!@symbols)/, '@brackets'],
                    [/@symbols/, {
                        cases: {
                            '@operators': 'operator',
                            '@default': ''
                        }
                    }],

                    // @ annotations.
                    // As an example, we emit a debugging log message on these tokens.
                    // Note: message are supressed during the first load -- change some lines to see them.
                    [/@\s*[a-zA-Z_\$][\w\$]*/, {token: 'annotation', log: 'annotation token: $0'}],

                    // numbers
                    [/\d*\.\d+([eE][\-+]?\d+)?/, 'number.float'],
                    [/0[xX][0-9a-fA-F]+/, 'number.hex'],
                    [/\d+/, 'number'],

                    // delimiter: after number because of .\d floats
                    [/[;,.]/, 'delimiter'],

                    // strings
                    [/"([^"\\]|\\.)*$/, 'string.invalid'],  // non-teminated string
                    [/"/, {token: 'string.quote', bracket: '@open', next: '@string'}],

                    // characters
                    [/'[^\\']'/, 'string'],
                    [/(')(@escapes)(')/, ['string', 'string.escape', 'string']],
                    [/'/, 'string.invalid']
                ],

                comment: [
                    [/[^\/*]+/, 'comment'],
                    [/\/\*/, 'comment', '@push'],    // nested comment
                    ["\\*/", 'comment', '@pop'],
                    [/[\/*]/, 'comment']
                ],

                string: [
                    [/[^\\"]+/, 'string'],
                    [/@escapes/, 'string.escape'],
                    [/\\./, 'string.escape.invalid'],
                    [/"/, {token: 'string.quote', bracket: '@close', next: '@pop'}]
                ],

                whitespace: [
                    [/[ \t\r\n]+/, 'white'],
                    [/\/\*/, 'comment', '@comment'],
                    [/\/\/.*$/, 'comment'],
                ],
            },
        });

    }

    editorDidMount(editor, monaco) {
        editor.focus();
        editor.layout();
        global.context.layout = function () {
            editor.layout();
        };
        window.onresize = function () {
            global.context.layout();
        }
    }

    onChange(newValue, e) {
        global.context.code = newValue;
    }

    render() {
        const code = this.state.code;
        const options = {
            selectOnLineNumbers: true,
            wrappingIndent: "indent",
            wrappingColumn: 0
        };
        return (
            <MonacoEditor
                width="100%"
                height="100%"
                language="mwgTask"
                value={code}
                options={options}
                onChange={this.onChange}
                editorDidMount={this.editorDidMount}
                editorWillMount={this.editorWillMount}
                theme="vs"
            />
        );
    }
}

export default TaskEditor;
