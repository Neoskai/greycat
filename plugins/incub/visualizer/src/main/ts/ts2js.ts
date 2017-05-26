interface Window {
    ts?:any;
}

module org.mwg.plugin.visualizer.ts2js {
    export function transpile(input : string) : string {
        console.log("toto");
        var sourceFile = window.ts.createSourceFile("query.ts", input, window.ts.ScriptTarget.ES5);
        // Output
        var outputText;
        var program = window.ts.createProgram(["query.ts"], {}, {
            getSourceFile: function (fileName) { return fileName.indexOf("query") === 0 ? sourceFile : undefined; },
            writeFile: function (_name, text) { outputText = text; },
            getDefaultLibFileName: function () { return "lib.d.ts"; },
            useCaseSensitiveFileNames: function () { return false; },
            getCanonicalFileName: function (fileName) { return fileName; },
            getCurrentDirectory: function () { return ""; },
            getNewLine: function () { return "\r\n"; },
            fileExists: function (fileName) { return fileName === "query.ts"; },
            readFile: function () { return ""; },
            directoryExists: function () { return true; },
            getDirectories: function () { return []; }
        });
        // Emit
        program.emit();
        if (outputText === undefined) {
            throw new Error("Output generation failed");
        }
        console.log(outputText);
        return outputText;
    }
}