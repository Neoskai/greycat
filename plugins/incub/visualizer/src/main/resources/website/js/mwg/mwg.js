var __extends = (this && this.__extends) || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
};
var java;
(function (java) {
    var lang;
    (function (lang) {
        var System = (function () {
            function System() {
            }
            System.gc = function () {
            };
            System.arraycopy = function (src, srcPos, dest, destPos, numElements) {
                if ((dest instanceof Float64Array || dest instanceof Int32Array)
                    && (src instanceof Float64Array || src instanceof Int32Array)) {
                    if (numElements == src.length) {
                        dest.set(src, destPos);
                    }
                    else {
                        dest.set(src.subarray(srcPos, srcPos + numElements), destPos);
                    }
                }
                else {
                    for (var i = 0; i < numElements; i++) {
                        dest[destPos + i] = src[srcPos + i];
                    }
                }
            };
            return System;
        }());
        lang.System = System;
        var StringBuilder = (function () {
            function StringBuilder() {
                this._buffer = "";
                this.length = 0;
            }
            StringBuilder.prototype.append = function (val) {
                this._buffer = this._buffer + val;
                this.length = this._buffer.length;
                return this;
            };
            StringBuilder.prototype.insert = function (position, val) {
                this._buffer = this._buffer.slice(0, position) + val + this._buffer.slice(position);
                return this;
            };
            StringBuilder.prototype.toString = function () {
                return this._buffer;
            };
            return StringBuilder;
        }());
        lang.StringBuilder = StringBuilder;
        var String = (function () {
            function String() {
            }
            String.valueOf = function (data, offset, count) {
                if (typeof offset === 'undefined' && typeof count === 'undefined') {
                    return data + '';
                }
                else {
                    return data.slice(offset, offset + count);
                }
            };
            String.hashCode = function (str) {
                var h = str['_hashCode'] ? str['_hashCode'] : 0;
                if (h === 0 && str.length > 0) {
                    var val = str;
                    for (var i = 0; i < str.length; i++) {
                        h = 31 * h + str.charCodeAt(i);
                    }
                    str['_hashCode'] = h;
                }
                return h;
            };
            String.isEmpty = function (str) {
                return str.length === 0;
            };
            String.join = function (delimiter, elements) {
                return elements.join(delimiter);
            };
            return String;
        }());
        lang.String = String;
        var Thread = (function () {
            function Thread() {
            }
            Thread.sleep = function (time) {
            };
            return Thread;
        }());
        lang.Thread = Thread;
        var Double = (function () {
            function Double() {
            }
            Double.MAX_VALUE = Number.MAX_VALUE;
            Double.POSITIVE_INFINITY = Number.POSITIVE_INFINITY;
            Double.NEGATIVE_INFINITY = Number.NEGATIVE_INFINITY;
            Double.NaN = NaN;
            return Double;
        }());
        lang.Double = Double;
        var Long = (function () {
            function Long() {
            }
            Long.parseLong = function (d) {
                return parseInt(d);
            };
            return Long;
        }());
        lang.Long = Long;
        var Integer = (function () {
            function Integer() {
            }
            Integer.parseInt = function (d) {
                return parseInt(d);
            };
            return Integer;
        }());
        lang.Integer = Integer;
    })(lang = java.lang || (java.lang = {}));
    var util;
    (function (util) {
        var concurrent;
        (function (concurrent) {
            var atomic;
            (function (atomic) {
                var AtomicIntegerArray = (function () {
                    function AtomicIntegerArray(initialCapacity) {
                        this._internal = new Int32Array(initialCapacity);
                    }
                    AtomicIntegerArray.prototype.set = function (index, newVal) {
                        this._internal[index] = newVal;
                    };
                    AtomicIntegerArray.prototype.get = function (index) {
                        return this._internal[index];
                    };
                    AtomicIntegerArray.prototype.getAndSet = function (index, newVal) {
                        var temp = this._internal[index];
                        this._internal[index] = newVal;
                        return temp;
                    };
                    AtomicIntegerArray.prototype.compareAndSet = function (index, expect, update) {
                        if (this._internal[index] == expect) {
                            this._internal[index] = update;
                            return true;
                        }
                        else {
                            return false;
                        }
                    };
                    return AtomicIntegerArray;
                }());
                atomic.AtomicIntegerArray = AtomicIntegerArray;
                var AtomicLongArray = (function () {
                    function AtomicLongArray(initialCapacity) {
                        this._internal = new Float64Array(initialCapacity);
                    }
                    AtomicLongArray.prototype.set = function (index, newVal) {
                        this._internal[index] = newVal;
                    };
                    AtomicLongArray.prototype.get = function (index) {
                        return this._internal[index];
                    };
                    AtomicLongArray.prototype.getAndSet = function (index, newVal) {
                        var temp = this._internal[index];
                        this._internal[index] = newVal;
                        return temp;
                    };
                    AtomicLongArray.prototype.compareAndSet = function (index, expect, update) {
                        if (this._internal[index] == expect) {
                            this._internal[index] = update;
                            return true;
                        }
                        else {
                            return false;
                        }
                    };
                    AtomicLongArray.prototype.length = function () {
                        return this._internal.length;
                    };
                    return AtomicLongArray;
                }());
                atomic.AtomicLongArray = AtomicLongArray;
                var AtomicReferenceArray = (function () {
                    function AtomicReferenceArray(initialCapacity) {
                        this._internal = new Array();
                    }
                    AtomicReferenceArray.prototype.set = function (index, newVal) {
                        this._internal[index] = newVal;
                    };
                    AtomicReferenceArray.prototype.get = function (index) {
                        return this._internal[index];
                    };
                    AtomicReferenceArray.prototype.getAndSet = function (index, newVal) {
                        var temp = this._internal[index];
                        this._internal[index] = newVal;
                        return temp;
                    };
                    AtomicReferenceArray.prototype.compareAndSet = function (index, expect, update) {
                        if (this._internal[index] == expect) {
                            this._internal[index] = update;
                            return true;
                        }
                        else {
                            return false;
                        }
                    };
                    AtomicReferenceArray.prototype.length = function () {
                        return this._internal.length;
                    };
                    return AtomicReferenceArray;
                }());
                atomic.AtomicReferenceArray = AtomicReferenceArray;
                var AtomicReference = (function () {
                    function AtomicReference() {
                        this._internal = null;
                    }
                    AtomicReference.prototype.compareAndSet = function (expect, update) {
                        if (this._internal == expect) {
                            this._internal = update;
                            return true;
                        }
                        else {
                            return false;
                        }
                    };
                    AtomicReference.prototype.get = function () {
                        return this._internal;
                    };
                    AtomicReference.prototype.set = function (newRef) {
                        this._internal = newRef;
                    };
                    AtomicReference.prototype.getAndSet = function (newVal) {
                        var temp = this._internal;
                        this._internal = newVal;
                        return temp;
                    };
                    return AtomicReference;
                }());
                atomic.AtomicReference = AtomicReference;
                var AtomicLong = (function () {
                    function AtomicLong(init) {
                        this._internal = 0;
                        this._internal = init;
                    }
                    AtomicLong.prototype.compareAndSet = function (expect, update) {
                        if (this._internal == expect) {
                            this._internal = update;
                            return true;
                        }
                        else {
                            return false;
                        }
                    };
                    AtomicLong.prototype.get = function () {
                        return this._internal;
                    };
                    AtomicLong.prototype.incrementAndGet = function () {
                        this._internal++;
                        return this._internal;
                    };
                    AtomicLong.prototype.decrementAndGet = function () {
                        this._internal--;
                        return this._internal;
                    };
                    return AtomicLong;
                }());
                atomic.AtomicLong = AtomicLong;
                var AtomicBoolean = (function () {
                    function AtomicBoolean(init) {
                        this._internal = false;
                        this._internal = init;
                    }
                    AtomicBoolean.prototype.compareAndSet = function (expect, update) {
                        if (this._internal == expect) {
                            this._internal = update;
                            return true;
                        }
                        else {
                            return false;
                        }
                    };
                    AtomicBoolean.prototype.get = function () {
                        return this._internal;
                    };
                    AtomicBoolean.prototype.set = function (newVal) {
                        this._internal = newVal;
                    };
                    return AtomicBoolean;
                }());
                atomic.AtomicBoolean = AtomicBoolean;
                var AtomicInteger = (function () {
                    function AtomicInteger(init) {
                        this._internal = 0;
                        this._internal = init;
                    }
                    AtomicInteger.prototype.compareAndSet = function (expect, update) {
                        if (this._internal == expect) {
                            this._internal = update;
                            return true;
                        }
                        else {
                            return false;
                        }
                    };
                    AtomicInteger.prototype.get = function () {
                        return this._internal;
                    };
                    AtomicInteger.prototype.set = function (newVal) {
                        this._internal = newVal;
                    };
                    AtomicInteger.prototype.getAndSet = function (newVal) {
                        var temp = this._internal;
                        this._internal = newVal;
                        return temp;
                    };
                    AtomicInteger.prototype.incrementAndGet = function () {
                        this._internal++;
                        return this._internal;
                    };
                    AtomicInteger.prototype.decrementAndGet = function () {
                        this._internal--;
                        return this._internal;
                    };
                    AtomicInteger.prototype.getAndIncrement = function () {
                        var temp = this._internal;
                        this._internal++;
                        return temp;
                    };
                    AtomicInteger.prototype.getAndDecrement = function () {
                        var temp = this._internal;
                        this._internal--;
                        return temp;
                    };
                    return AtomicInteger;
                }());
                atomic.AtomicInteger = AtomicInteger;
            })(atomic = concurrent.atomic || (concurrent.atomic = {}));
            var locks;
            (function (locks) {
                var ReentrantLock = (function () {
                    function ReentrantLock() {
                    }
                    ReentrantLock.prototype.lock = function () {
                    };
                    ReentrantLock.prototype.unlock = function () {
                    };
                    return ReentrantLock;
                }());
                locks.ReentrantLock = ReentrantLock;
            })(locks = concurrent.locks || (concurrent.locks = {}));
        })(concurrent = util.concurrent || (util.concurrent = {}));
        var Random = (function () {
            function Random() {
                this.seed = undefined;
            }
            Random.prototype.nextInt = function (max) {
                if (typeof max === 'undefined') {
                    max = Math.pow(2, 32);
                }
                if (this.seed == undefined) {
                    return Math.floor(Math.random() * max);
                }
                else {
                    return Math.floor(this.nextSeeded(0, max));
                }
            };
            Random.prototype.nextDouble = function () {
                if (this.seed == undefined) {
                    return Math.random();
                }
                else {
                    return this.nextSeeded();
                }
            };
            Random.prototype.nextBoolean = function () {
                if (this.seed == undefined) {
                    return Math.random() >= 0.5;
                }
                else {
                    return this.nextSeeded() >= 0.5;
                }
            };
            Random.prototype.setSeed = function (seed) {
                this.seed = seed;
            };
            Random.prototype.nextSeeded = function (min, max) {
                var max = max || 1;
                var min = min || 0;
                this.seed = (this.seed * 9301 + 49297) % 233280;
                var rnd = this.seed / 233280;
                return min + rnd * (max - min);
            };
            return Random;
        }());
        util.Random = Random;
        var Arrays = (function () {
            function Arrays() {
            }
            Arrays.fill = function (data, begin, nbElem, param) {
                var max = begin + nbElem;
                for (var i = begin; i < max; i++) {
                    data[i] = param;
                }
            };
            Arrays.copyOf = function (original, newLength, ignore) {
                var copy = new Array(newLength);
                lang.System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
                return copy;
            };
            return Arrays;
        }());
        util.Arrays = Arrays;
        var Collections = (function () {
            function Collections() {
            }
            Collections.swap = function (list, i, j) {
                var l = list;
                l.set(i, l.set(j, l.get(i)));
            };
            return Collections;
        }());
        util.Collections = Collections;
        var Itr = (function () {
            function Itr(list) {
                this.cursor = 0;
                this.lastRet = -1;
                this.list = list;
            }
            Itr.prototype.hasNext = function () {
                return this.cursor != this.list.size();
            };
            Itr.prototype.next = function () {
                try {
                    var i = this.cursor;
                    var next = this.list.get(i);
                    this.lastRet = i;
                    this.cursor = i + 1;
                    return next;
                }
                catch ($ex$) {
                    if ($ex$ instanceof Error) {
                        var e = $ex$;
                        throw new Error("no such element exception");
                    }
                    else {
                        throw $ex$;
                    }
                }
            };
            return Itr;
        }());
        util.Itr = Itr;
        var HashSet = (function () {
            function HashSet() {
                this.content = {};
            }
            HashSet.prototype.add = function (val) {
                this.content[val] = val;
            };
            HashSet.prototype.clear = function () {
                this.content = {};
            };
            HashSet.prototype.contains = function (val) {
                return this.content.hasOwnProperty(val);
            };
            HashSet.prototype.containsAll = function (elems) {
                return false;
            };
            HashSet.prototype.addAll = function (vals) {
                var tempArray = vals.toArray(null);
                for (var i = 0; i < tempArray.length; i++) {
                    this.content[tempArray[i]] = tempArray[i];
                }
                return true;
            };
            HashSet.prototype.remove = function (val) {
                var b = false;
                if (this.content[val]) {
                    b = true;
                }
                delete this.content[val];
                return b;
            };
            HashSet.prototype.removeAll = function () {
                return false;
            };
            HashSet.prototype.size = function () {
                return Object.keys(this.content).length;
            };
            HashSet.prototype.isEmpty = function () {
                return this.size() == 0;
            };
            HashSet.prototype.toArray = function (a) {
                var _this = this;
                return Object.keys(this.content).map(function (key) { return _this.content[key]; });
            };
            HashSet.prototype.iterator = function () {
                return new java.util.Itr(this);
            };
            HashSet.prototype.forEach = function (f) {
                for (var p in this.content) {
                    f(this.content[p]);
                }
            };
            HashSet.prototype.get = function (index) {
                return this.content[index];
            };
            return HashSet;
        }());
        util.HashSet = HashSet;
        var AbstractList = (function () {
            function AbstractList() {
                this.content = [];
            }
            AbstractList.prototype.addAll = function (index, vals) {
                var tempArray = vals.toArray(null);
                for (var i = 0; i < tempArray.length; i++) {
                    this.content.push(tempArray[i]);
                }
                return false;
            };
            AbstractList.prototype.clear = function () {
                this.content = [];
            };
            AbstractList.prototype.poll = function () {
                return this.content.shift();
            };
            AbstractList.prototype.remove = function (indexOrElem) {
                this.content.splice(indexOrElem, 1);
                return true;
            };
            AbstractList.prototype.removeAll = function () {
                this.content = [];
                return true;
            };
            AbstractList.prototype.toArray = function (a) {
                return this.content;
            };
            AbstractList.prototype.size = function () {
                return this.content.length;
            };
            AbstractList.prototype.add = function (index, elem) {
                if (typeof elem !== 'undefined') {
                    this.content.splice(index, 0, elem);
                }
                else {
                    this.content.push(index);
                }
            };
            AbstractList.prototype.get = function (index) {
                return this.content[index];
            };
            AbstractList.prototype.contains = function (val) {
                return this.content.indexOf(val) != -1;
            };
            AbstractList.prototype.containsAll = function (elems) {
                return false;
            };
            AbstractList.prototype.isEmpty = function () {
                return this.content.length == 0;
            };
            AbstractList.prototype.set = function (index, element) {
                this.content[index] = element;
                return element;
            };
            AbstractList.prototype.indexOf = function (element) {
                return this.content.indexOf(element);
            };
            AbstractList.prototype.lastIndexOf = function (element) {
                return this.content.lastIndexOf(element);
            };
            AbstractList.prototype.iterator = function () {
                return new Itr(this);
            };
            return AbstractList;
        }());
        util.AbstractList = AbstractList;
        var LinkedList = (function (_super) {
            __extends(LinkedList, _super);
            function LinkedList() {
                _super.apply(this, arguments);
            }
            return LinkedList;
        }(AbstractList));
        util.LinkedList = LinkedList;
        var ArrayList = (function (_super) {
            __extends(ArrayList, _super);
            function ArrayList() {
                _super.apply(this, arguments);
            }
            return ArrayList;
        }(AbstractList));
        util.ArrayList = ArrayList;
        var Stack = (function () {
            function Stack() {
                this.content = new Array();
            }
            Stack.prototype.pop = function () {
                return this.content.pop();
            };
            Stack.prototype.push = function (t) {
                this.content.push(t);
            };
            Stack.prototype.isEmpty = function () {
                return this.content.length == 0;
            };
            Stack.prototype.peek = function () {
                return this.content.slice(-1)[0];
            };
            return Stack;
        }());
        util.Stack = Stack;
        var HashMap = (function () {
            function HashMap() {
                this.content = {};
            }
            HashMap.prototype.get = function (key) {
                return this.content[key];
            };
            HashMap.prototype.put = function (key, value) {
                var previous_val = this.content[key];
                this.content[key] = value;
                return previous_val;
            };
            HashMap.prototype.containsKey = function (key) {
                return this.content.hasOwnProperty(key);
            };
            HashMap.prototype.remove = function (key) {
                var tmp = this.content[key];
                delete this.content[key];
                return tmp;
            };
            HashMap.prototype.keySet = function () {
                var result = new HashSet();
                for (var p in this.content) {
                    if (this.content.hasOwnProperty(p)) {
                        result.add(p);
                    }
                }
                return result;
            };
            HashMap.prototype.isEmpty = function () {
                return Object.keys(this.content).length == 0;
            };
            HashMap.prototype.values = function () {
                var result = new HashSet();
                for (var p in this.content) {
                    if (this.content.hasOwnProperty(p)) {
                        result.add(this.content[p]);
                    }
                }
                return result;
            };
            HashMap.prototype.clear = function () {
                this.content = {};
            };
            HashMap.prototype.size = function () {
                return Object.keys(this.content).length;
            };
            return HashMap;
        }());
        util.HashMap = HashMap;
        var ConcurrentHashMap = (function (_super) {
            __extends(ConcurrentHashMap, _super);
            function ConcurrentHashMap() {
                _super.apply(this, arguments);
            }
            return ConcurrentHashMap;
        }(HashMap));
        util.ConcurrentHashMap = ConcurrentHashMap;
    })(util = java.util || (java.util = {}));
})(java || (java = {}));
function arrayInstanceOf(arr, arg) {
    if (!(arr instanceof Array)) {
        return false;
    }
    else {
        if (arr.length == 0) {
            return true;
        }
        else {
            return (arr[0] instanceof arg);
        }
    }
}
var Long = (function () {
    function Long(low, high, unsigned) {
        /*
         long.js (c) 2013 Daniel Wirtz <dcode@dcode.io>
         Released under the Apache License, Version 2.0
         see: https://github.com/dcodeIO/long.js for details
         */
        this.high = 0;
        this.low = 0;
        this.unsigned = false;
        this.eq = this.equals;
        this.neq = this.notEquals;
        this.lt = this.lessThan;
        this.lte = this.lessThanOrEqual;
        this.gt = this.greaterThan;
        this.gte = this.greaterThanOrEqual;
        this.comp = this.compare;
        this.neg = this.negate;
        this.sub = this.subtract;
        this.mul = this.multiply;
        this.div = this.divide;
        this.mod = this.modulo;
        this.shl = this.shiftLeft;
        this.shr = this.shiftRight;
        this.shru = this.shiftRightUnsigned;
        if (!(high == undefined)) {
            this.high = high;
        }
        if (!(low == undefined)) {
            this.low = low;
        }
        if (!(unsigned == undefined)) {
            this.unsigned = unsigned;
        }
    }
    Long.isLong = function (obj) {
        return (obj && obj["__isLong__"]) === true;
    };
    Long.fromInt = function (value, unsigned) {
        var obj, cachedObj, cache;
        if (unsigned) {
            value >>>= 0;
            if (cache = (0 <= value && value < 256)) {
                cachedObj = Long.UINT_CACHE[value];
                if (cachedObj)
                    return cachedObj;
            }
            obj = Long.fromBits(value, (value | 0) < 0 ? -1 : 0, true);
            if (cache)
                Long.UINT_CACHE[value] = obj;
            return obj;
        }
        else {
            value |= 0;
            if (cache = (-128 <= value && value < 128)) {
                cachedObj = Long.INT_CACHE[value];
                if (cachedObj)
                    return cachedObj;
            }
            obj = Long.fromBits(value, value < 0 ? -1 : 0, false);
            if (cache)
                Long.INT_CACHE[value] = obj;
            return obj;
        }
    };
    Long.fromNumber = function (value, unsigned) {
        if (isNaN(value) || !isFinite(value))
            return unsigned ? Long.UZERO : Long.ZERO;
        if (unsigned) {
            if (value < 0)
                return Long.UZERO;
            if (value >= Long.TWO_PWR_64_DBL)
                return Long.MAX_UNSIGNED_VALUE;
        }
        else {
            if (value <= -Long.TWO_PWR_63_DBL)
                return Long.MIN_VALUE;
            if (value + 1 >= Long.TWO_PWR_63_DBL)
                return Long.MAX_VALUE;
        }
        if (value < 0)
            return Long.fromNumber(-value, unsigned).neg();
        return Long.fromBits((value % Long.TWO_PWR_32_DBL) | 0, (value / Long.TWO_PWR_32_DBL) | 0, unsigned);
    };
    Long.fromBits = function (lowBits, highBits, unsigned) {
        return new Long(lowBits, highBits, unsigned);
    };
    Long.fromString = function (str, radix, unsigned) {
        if (radix === void 0) { radix = 10; }
        if (unsigned === void 0) { unsigned = false; }
        if (str.length === 0)
            throw Error('empty string');
        if (str === "NaN" || str === "Infinity" || str === "+Infinity" || str === "-Infinity")
            return Long.ZERO;
        radix = radix || 10;
        if (radix < 2 || 36 < radix)
            throw RangeError('radix');
        var p;
        if ((p = str.indexOf('-')) > 0)
            throw Error('interior hyphen');
        else if (p === 0) {
            return Long.fromString(str.substring(1), radix, unsigned).neg();
        }
        // Do several (8) digits each time through the loop, so as to
        // minimize the calls to the very expensive emulated div.
        var radixToPower = Long.fromNumber(Long.pow_dbl(radix, 8));
        var result = Long.ZERO;
        for (var i = 0; i < str.length; i += 8) {
            var size = Math.min(8, str.length - i), value = parseInt(str.substring(i, i + size), radix);
            if (size < 8) {
                var power = Long.fromNumber(Long.pow_dbl(radix, size));
                result = result.mul(power).add(Long.fromNumber(value));
            }
            else {
                result = result.mul(radixToPower);
                result = result.add(Long.fromNumber(value));
            }
        }
        result.unsigned = unsigned;
        return result;
    };
    Long.fromValue = function (val) {
        if (val /* is compatible */ instanceof Long)
            return val;
        if (typeof val === 'number')
            return Long.fromNumber(val);
        if (typeof val === 'string')
            return Long.fromString(val);
        // Throws for non-objects, converts non-instanceof Long:
        return Long.fromBits(val.low, val.high, val.unsigned);
    };
    Long.prototype.toInt = function () {
        return this.unsigned ? this.low >>> 0 : this.low;
    };
    ;
    Long.prototype.toNumber = function () {
        if (this.unsigned)
            return ((this.high >>> 0) * Long.TWO_PWR_32_DBL) + (this.low >>> 0);
        return this.high * Long.TWO_PWR_32_DBL + (this.low >>> 0);
    };
    ;
    Long.prototype.toString = function (radix) {
        radix = radix || 10;
        if (radix < 2 || 36 < radix)
            throw RangeError('radix');
        if (this.isZero())
            return '0';
        if (this.isNegative()) {
            if (this.eq(Long.MIN_VALUE)) {
                // We need to change the Long value before it can be negated, so we remove
                // the bottom-most digit in this base and then recurse to do the rest.
                var radixLong = Long.fromNumber(radix), div = this.div(radixLong), rem1 = div.mul(radixLong).sub(this);
                return div.toString(radix) + rem1.toInt().toString(radix);
            }
            else
                return '-' + this.neg().toString(radix);
        }
        // Do several (6) digits each time through the loop, so as to
        // minimize the calls to the very expensive emulated div.
        var radixToPower = Long.fromNumber(Long.pow_dbl(radix, 6), this.unsigned);
        var rem = this;
        var result = '';
        while (true) {
            var remDiv = rem.div(radixToPower);
            var intval = rem.sub(remDiv.mul(radixToPower)).toInt() >>> 0;
            var digits = intval.toString(radix);
            rem = remDiv;
            if (rem.isZero())
                return digits + result;
            else {
                while (digits.length < 6)
                    digits = '0' + digits;
                result = '' + digits + result;
            }
        }
    };
    ;
    Long.prototype.getHighBits = function () {
        return this.high;
    };
    ;
    Long.prototype.getHighBitsUnsigned = function () {
        return this.high >>> 0;
    };
    ;
    Long.prototype.getLowBits = function () {
        return this.low;
    };
    ;
    Long.prototype.getLowBitsUnsigned = function () {
        return this.low >>> 0;
    };
    ;
    Long.prototype.getNumBitsAbs = function () {
        if (this.isNegative())
            return this.eq(Long.MIN_VALUE) ? 64 : this.neg().getNumBitsAbs();
        var val = this.high != 0 ? this.high : this.low;
        for (var bit = 31; bit > 0; bit--)
            if ((val & (1 << bit)) != 0)
                break;
        return this.high != 0 ? bit + 33 : bit + 1;
    };
    ;
    Long.prototype.isZero = function () {
        return this.high === 0 && this.low === 0;
    };
    ;
    Long.prototype.isNegative = function () {
        return !this.unsigned && this.high < 0;
    };
    ;
    Long.prototype.isPositive = function () {
        return this.unsigned || this.high >= 0;
    };
    ;
    Long.prototype.isOdd = function () {
        return (this.low & 1) === 1;
    };
    ;
    Long.prototype.isEven = function () {
        return (this.low & 1) === 0;
    };
    ;
    Long.prototype.equals = function (other) {
        if (!Long.isLong(other))
            other = Long.fromValue(other);
        if (this.unsigned !== other.unsigned && (this.high >>> 31) === 1 && (other.high >>> 31) === 1)
            return false;
        return this.high === other.high && this.low === other.low;
    };
    ;
    Long.prototype.notEquals = function (other) {
        return !this.eq(other);
    };
    ;
    Long.prototype.lessThan = function (other) {
        return this.comp(other) < 0;
    };
    ;
    Long.prototype.lessThanOrEqual = function (other) {
        return this.comp(other) <= 0;
    };
    ;
    Long.prototype.greaterThan = function (other) {
        return this.comp(other) > 0;
    };
    ;
    Long.prototype.greaterThanOrEqual = function (other) {
        return this.comp(other) >= 0;
    };
    ;
    Long.prototype.compare = function (other) {
        if (!Long.isLong(other))
            other = Long.fromValue(other);
        if (this.eq(other))
            return 0;
        var thisNeg = this.isNegative(), otherNeg = other.isNegative();
        if (thisNeg && !otherNeg)
            return -1;
        if (!thisNeg && otherNeg)
            return 1;
        // At this point the sign bits are the same
        if (!this.unsigned)
            return this.sub(other).isNegative() ? -1 : 1;
        // Both are positive if at least one is unsigned
        return (other.high >>> 0) > (this.high >>> 0) || (other.high === this.high && (other.low >>> 0) > (this.low >>> 0)) ? -1 : 1;
    };
    ;
    Long.prototype.negate = function () {
        if (!this.unsigned && this.eq(Long.MIN_VALUE))
            return Long.MIN_VALUE;
        return this.not().add(Long.ONE);
    };
    ;
    Long.prototype.add = function (addend) {
        if (!Long.isLong(addend)) {
            addend = Long.fromValue(addend);
        }
        // Divide each number into 4 chunks of 16 bits, and then sum the chunks.
        var a48 = this.high >>> 16;
        var a32 = this.high & 0xFFFF;
        var a16 = this.low >>> 16;
        var a00 = this.low & 0xFFFF;
        var b48 = addend.high >>> 16;
        var b32 = addend.high & 0xFFFF;
        var b16 = addend.low >>> 16;
        var b00 = addend.low & 0xFFFF;
        var c48 = 0, c32 = 0, c16 = 0, c00 = 0;
        c00 += a00 + b00;
        c16 += c00 >>> 16;
        c00 &= 0xFFFF;
        c16 += a16 + b16;
        c32 += c16 >>> 16;
        c16 &= 0xFFFF;
        c32 += a32 + b32;
        c48 += c32 >>> 16;
        c32 &= 0xFFFF;
        c48 += a48 + b48;
        c48 &= 0xFFFF;
        return Long.fromBits((c16 << 16) | c00, (c48 << 16) | c32, this.unsigned);
    };
    ;
    Long.prototype.subtract = function (subtrahend) {
        if (!Long.isLong(subtrahend))
            subtrahend = Long.fromValue(subtrahend);
        return this.add(subtrahend.neg());
    };
    ;
    Long.prototype.multiply = function (multiplier) {
        if (this.isZero())
            return Long.ZERO;
        if (!Long.isLong(multiplier))
            multiplier = Long.fromValue(multiplier);
        if (multiplier.isZero())
            return Long.ZERO;
        if (this.eq(Long.MIN_VALUE))
            return multiplier.isOdd() ? Long.MIN_VALUE : Long.ZERO;
        if (multiplier.eq(Long.MIN_VALUE))
            return this.isOdd() ? Long.MIN_VALUE : Long.ZERO;
        if (this.isNegative()) {
            if (multiplier.isNegative())
                return this.neg().mul(multiplier.neg());
            else
                return this.neg().mul(multiplier).neg();
        }
        else if (multiplier.isNegative())
            return this.mul(multiplier.neg()).neg();
        // If both longs are small, use float multiplication
        if (this.lt(Long.TWO_PWR_24) && multiplier.lt(Long.TWO_PWR_24))
            return Long.fromNumber(this.toNumber() * multiplier.toNumber(), this.unsigned);
        // Divide each long into 4 chunks of 16 bits, and then add up 4x4 products.
        // We can skip products that would overflow.
        var a48 = this.high >>> 16;
        var a32 = this.high & 0xFFFF;
        var a16 = this.low >>> 16;
        var a00 = this.low & 0xFFFF;
        var b48 = multiplier.high >>> 16;
        var b32 = multiplier.high & 0xFFFF;
        var b16 = multiplier.low >>> 16;
        var b00 = multiplier.low & 0xFFFF;
        var c48 = 0, c32 = 0, c16 = 0, c00 = 0;
        c00 += a00 * b00;
        c16 += c00 >>> 16;
        c00 &= 0xFFFF;
        c16 += a16 * b00;
        c32 += c16 >>> 16;
        c16 &= 0xFFFF;
        c16 += a00 * b16;
        c32 += c16 >>> 16;
        c16 &= 0xFFFF;
        c32 += a32 * b00;
        c48 += c32 >>> 16;
        c32 &= 0xFFFF;
        c32 += a16 * b16;
        c48 += c32 >>> 16;
        c32 &= 0xFFFF;
        c32 += a00 * b32;
        c48 += c32 >>> 16;
        c32 &= 0xFFFF;
        c48 += a48 * b00 + a32 * b16 + a16 * b32 + a00 * b48;
        c48 &= 0xFFFF;
        return Long.fromBits((c16 << 16) | c00, (c48 << 16) | c32, this.unsigned);
    };
    ;
    Long.prototype.divide = function (divisor) {
        if (!Long.isLong(divisor))
            divisor = Long.fromValue(divisor);
        if (divisor.isZero())
            throw Error('division by zero');
        if (this.isZero())
            return this.unsigned ? Long.UZERO : Long.ZERO;
        var approx, rem, res;
        if (!this.unsigned) {
            // This section is only relevant for signed longs and is derived from the
            // closure library as a whole.
            if (this.eq(Long.MIN_VALUE)) {
                if (divisor.eq(Long.ONE) || divisor.eq(Long.NEG_ONE))
                    return Long.MIN_VALUE; // recall that -MIN_VALUE == MIN_VALUE
                else if (divisor.eq(Long.MIN_VALUE))
                    return Long.ONE;
                else {
                    // At this point, we have |other| >= 2, so |this/other| < |MIN_VALUE|.
                    var halfThis = this.shr(1);
                    approx = halfThis.div(divisor).shl(1);
                    if (approx.eq(Long.ZERO)) {
                        return divisor.isNegative() ? Long.ONE : Long.NEG_ONE;
                    }
                    else {
                        rem = this.sub(divisor.mul(approx));
                        res = approx.add(rem.div(divisor));
                        return res;
                    }
                }
            }
            else if (divisor.eq(Long.MIN_VALUE))
                return this.unsigned ? Long.UZERO : Long.ZERO;
            if (this.isNegative()) {
                if (divisor.isNegative())
                    return this.neg().div(divisor.neg());
                return this.neg().div(divisor).neg();
            }
            else if (divisor.isNegative())
                return this.div(divisor.neg()).neg();
            res = Long.ZERO;
        }
        else {
            // The algorithm below has not been made for unsigned longs. It's therefore
            // required to take special care of the MSB prior to running it.
            if (!divisor.unsigned)
                divisor = divisor.toUnsigned();
            if (divisor.gt(this))
                return Long.UZERO;
            if (divisor.gt(this.shru(1)))
                return Long.UONE;
            res = Long.UZERO;
        }
        // Repeat the following until the remainder is less than other:  find a
        // floating-point that approximates remainder / other *from below*, add this
        // into the result, and subtract it from the remainder.  It is critical that
        // the approximate value is less than or equal to the real value so that the
        // remainder never becomes negative.
        rem = this;
        while (rem.gte(divisor)) {
            // Approximate the result of division. This may be a little greater or
            // smaller than the actual value.
            approx = Math.max(1, Math.floor(rem.toNumber() / divisor.toNumber()));
            // We will tweak the approximate result by changing it in the 48-th digit or
            // the smallest non-fractional digit, whichever is larger.
            var log2 = Math.ceil(Math.log(approx) / Math.LN2), delta = (log2 <= 48) ? 1 : Long.pow_dbl(2, log2 - 48), 
            // Decrease the approximation until it is smaller than the remainder.  Note
            // that if it is too large, the product overflows and is negative.
            approxRes = Long.fromNumber(approx), approxRem = approxRes.mul(divisor);
            while (approxRem.isNegative() || approxRem.gt(rem)) {
                approx -= delta;
                approxRes = Long.fromNumber(approx, this.unsigned);
                approxRem = approxRes.mul(divisor);
            }
            // We know the answer can't be zero... and actually, zero would cause
            // infinite recursion since we would make no progress.
            if (approxRes.isZero())
                approxRes = Long.ONE;
            res = res.add(approxRes);
            rem = rem.sub(approxRem);
        }
        return res;
    };
    ;
    Long.prototype.modulo = function (divisor) {
        if (!Long.isLong(divisor))
            divisor = Long.fromValue(divisor);
        return this.sub(this.div(divisor).mul(divisor));
    };
    ;
    Long.prototype.not = function () {
        return Long.fromBits(~this.low, ~this.high, this.unsigned);
    };
    ;
    Long.prototype.and = function (other) {
        if (!Long.isLong(other))
            other = Long.fromValue(other);
        return Long.fromBits(this.low & other.low, this.high & other.high, this.unsigned);
    };
    ;
    Long.prototype.or = function (other) {
        if (!Long.isLong(other))
            other = Long.fromValue(other);
        return Long.fromBits(this.low | other.low, this.high | other.high, this.unsigned);
    };
    ;
    Long.prototype.xor = function (other) {
        if (!Long.isLong(other))
            other = Long.fromValue(other);
        return Long.fromBits(this.low ^ other.low, this.high ^ other.high, this.unsigned);
    };
    ;
    Long.prototype.shiftLeft = function (numBits) {
        if (Long.isLong(numBits))
            numBits = numBits.toInt();
        if ((numBits &= 63) === 0)
            return this;
        else if (numBits < 32)
            return Long.fromBits(this.low << numBits, (this.high << numBits) | (this.low >>> (32 - numBits)), this.unsigned);
        else
            return Long.fromBits(0, this.low << (numBits - 32), this.unsigned);
    };
    ;
    Long.prototype.shiftRight = function (numBits) {
        if (Long.isLong(numBits))
            numBits = numBits.toInt();
        if ((numBits &= 63) === 0)
            return this;
        else if (numBits < 32)
            return Long.fromBits((this.low >>> numBits) | (this.high << (32 - numBits)), this.high >> numBits, this.unsigned);
        else
            return Long.fromBits(this.high >> (numBits - 32), this.high >= 0 ? 0 : -1, this.unsigned);
    };
    ;
    Long.prototype.shiftRightUnsigned = function (numBits) {
        if (Long.isLong(numBits))
            numBits = numBits.toInt();
        numBits &= 63;
        if (numBits === 0)
            return this;
        else {
            var high = this.high;
            if (numBits < 32) {
                var low = this.low;
                return Long.fromBits((low >>> numBits) | (high << (32 - numBits)), high >>> numBits, this.unsigned);
            }
            else if (numBits === 32)
                return Long.fromBits(high, 0, this.unsigned);
            else
                return Long.fromBits(high >>> (numBits - 32), 0, this.unsigned);
        }
    };
    ;
    Long.prototype.toSigned = function () {
        if (!this.unsigned)
            return this;
        return Long.fromBits(this.low, this.high, false);
    };
    ;
    Long.prototype.toUnsigned = function () {
        if (this.unsigned)
            return this;
        return Long.fromBits(this.low, this.high, true);
    };
    ;
    Long.INT_CACHE = {};
    Long.UINT_CACHE = {};
    Long.pow_dbl = Math.pow;
    Long.TWO_PWR_16_DBL = 1 << 16;
    Long.TWO_PWR_24_DBL = 1 << 24;
    Long.TWO_PWR_32_DBL = Long.TWO_PWR_16_DBL * Long.TWO_PWR_16_DBL;
    Long.TWO_PWR_64_DBL = Long.TWO_PWR_32_DBL * Long.TWO_PWR_32_DBL;
    Long.TWO_PWR_63_DBL = Long.TWO_PWR_64_DBL / 2;
    Long.TWO_PWR_24 = Long.fromInt(Long.TWO_PWR_24_DBL);
    Long.ZERO = Long.fromInt(0);
    Long.UZERO = Long.fromInt(0, true);
    Long.ONE = Long.fromInt(1);
    Long.UONE = Long.fromInt(1, true);
    Long.NEG_ONE = Long.fromInt(-1);
    Long.MAX_VALUE = Long.fromBits(0x7FFFFFFF, 0xFFFFFFFF, false);
    Long.MAX_UNSIGNED_VALUE = Long.fromBits(0xFFFFFFFF, 0xFFFFFFFF, true);
    Long.MIN_VALUE = Long.fromBits(0x80000000, 0, false);
    return Long;
}());
Object.defineProperty(Long.prototype, "__isLong__", {
    value: true,
    enumerable: false,
    configurable: false
});
/// <reference path="./jre.ts" />
var org;
(function (org) {
    var mwg;
    (function (mwg) {
        var Constants = (function () {
            function Constants() {
            }
            Constants.isDefined = function (param) {
                return param != null;
            };
            Constants.equals = function (src, other) {
                return src === other;
            };
            Constants.longArrayEquals = function (src, other) {
                if (src.length != other.length) {
                    return false;
                }
                for (var i = 0; i < src.length; i++) {
                    if (src[i] != other[i]) {
                        return false;
                    }
                }
                return true;
            };
            Constants.KEY_SIZE = 4;
            Constants.LONG_SIZE = 53;
            Constants.PREFIX_SIZE = 16;
            Constants.BEGINNING_OF_TIME = -0x001FFFFFFFFFFFFE;
            Constants.END_OF_TIME = 0x001FFFFFFFFFFFFE;
            Constants.NULL_LONG = 0x001FFFFFFFFFFFFF;
            Constants.KEY_PREFIX_MASK = 0x0000001FFFFFFFFF;
            Constants.CACHE_MISS_ERROR = "Cache miss error";
            Constants.QUERY_SEP = ',';
            Constants.QUERY_KV_SEP = '=';
            Constants.TASK_SEP = '.';
            Constants.TASK_PARAM_OPEN = '(';
            Constants.TASK_PARAM_CLOSE = ')';
            Constants.CHUNK_SEP = "|".charCodeAt(0);
            Constants.CHUNK_SUB_SEP = ",".charCodeAt(0);
            Constants.CHUNK_SUB_SUB_SEP = ":".charCodeAt(0);
            Constants.CHUNK_SUB_SUB_SUB_SEP = "%".charCodeAt(0);
            Constants.BUFFER_SEP = "#".charCodeAt(0);
            Constants.KEY_SEP = ";".charCodeAt(0);
            Constants.MAP_INITIAL_CAPACITY = 8;
            Constants.MAP_LOAD_FACTOR = (75 / 100);
            Constants.BOOL_TRUE = "1".charCodeAt(0);
            Constants.BOOL_FALSE = "0".charCodeAt(0);
            return Constants;
        }());
        mwg.Constants = Constants;
        var GraphBuilder = (function () {
            function GraphBuilder() {
                this._storage = null;
                this._scheduler = null;
                this._plugins = null;
                this._memorySize = -1;
                this._readOnly = false;
            }
            GraphBuilder.prototype.withStorage = function (storage) {
                this._storage = storage;
                return this;
            };
            GraphBuilder.prototype.withReadOnlyStorage = function (storage) {
                this._storage = storage;
                this._readOnly = true;
                return this;
            };
            GraphBuilder.prototype.withMemorySize = function (numberOfElements) {
                this._memorySize = numberOfElements;
                return this;
            };
            GraphBuilder.prototype.withScheduler = function (scheduler) {
                this._scheduler = scheduler;
                return this;
            };
            GraphBuilder.prototype.withPlugin = function (plugin) {
                if (this._plugins == null) {
                    this._plugins = new Array(1);
                    this._plugins[0] = plugin;
                }
                else {
                    var _plugins2 = new Array(this._plugins.length + 1);
                    java.lang.System.arraycopy(this._plugins, 0, _plugins2, 0, this._plugins.length);
                    _plugins2[this._plugins.length] = plugin;
                    this._plugins = _plugins2;
                }
                return this;
            };
            GraphBuilder.prototype.build = function () {
                if (org.mwg.GraphBuilder._internalBuilder == null) {
                    org.mwg.GraphBuilder._internalBuilder = new org.mwg.core.Builder();
                }
                return org.mwg.GraphBuilder._internalBuilder.newGraph(this._storage, this._readOnly, this._scheduler, this._plugins, this._memorySize);
            };
            GraphBuilder._internalBuilder = null;
            return GraphBuilder;
        }());
        mwg.GraphBuilder = GraphBuilder;
        var Type = (function () {
            function Type() {
            }
            Type.typeName = function (p_type) {
                switch (p_type) {
                    case org.mwg.Type.BOOL:
                        return "boolean";
                    case org.mwg.Type.STRING:
                        return "string";
                    case org.mwg.Type.LONG:
                        return "long";
                    case org.mwg.Type.INT:
                        return "int";
                    case org.mwg.Type.DOUBLE:
                        return "double";
                    case org.mwg.Type.DOUBLE_ARRAY:
                        return "double[]";
                    case org.mwg.Type.LONG_ARRAY:
                        return "long[]";
                    case org.mwg.Type.INT_ARRAY:
                        return "int[]";
                    case org.mwg.Type.LONG_TO_LONG_MAP:
                        return "map(long->long)";
                    case org.mwg.Type.LONG_TO_LONG_ARRAY_MAP:
                        return "map(long->long[])";
                    case org.mwg.Type.STRING_TO_LONG_MAP:
                        return "map(string->long)";
                    case org.mwg.Type.RELATION:
                        return "relation";
                    default:
                        return "unknown";
                }
            };
            Type.BOOL = 1;
            Type.STRING = 2;
            Type.LONG = 3;
            Type.INT = 4;
            Type.DOUBLE = 5;
            Type.DOUBLE_ARRAY = 6;
            Type.LONG_ARRAY = 7;
            Type.INT_ARRAY = 8;
            Type.LONG_TO_LONG_MAP = 9;
            Type.LONG_TO_LONG_ARRAY_MAP = 10;
            Type.STRING_TO_LONG_MAP = 11;
            Type.RELATION = 12;
            return Type;
        }());
        mwg.Type = Type;
        var chunk;
        (function (chunk_1) {
            var ChunkType = (function () {
                function ChunkType() {
                }
                ChunkType.STATE_CHUNK = 0;
                ChunkType.TIME_TREE_CHUNK = 1;
                ChunkType.WORLD_ORDER_CHUNK = 2;
                ChunkType.GEN_CHUNK = 3;
                return ChunkType;
            }());
            chunk_1.ChunkType = ChunkType;
        })(chunk = mwg.chunk || (mwg.chunk = {}));
        var plugin;
        (function (plugin) {
            var AbstractNode = (function () {
                function AbstractNode(p_world, p_time, p_id, p_graph) {
                    this._index_worldOrder = -1;
                    this._index_superTimeTree = -1;
                    this._index_timeTree = -1;
                    this._index_stateChunk = -1;
                    this._world_magic = -1;
                    this._super_time_magic = -1;
                    this._time_magic = -1;
                    this._dead = false;
                    this._world = p_world;
                    this._time = p_time;
                    this._id = p_id;
                    this._graph = p_graph;
                    this._resolver = p_graph.resolver();
                }
                AbstractNode.prototype.cacheLock = function () {
                };
                AbstractNode.prototype.cacheUnlock = function () {
                };
                AbstractNode.prototype.init = function () { };
                AbstractNode.prototype.nodeTypeName = function () {
                    return this._resolver.typeName(this);
                };
                AbstractNode.prototype.unphasedState = function () {
                    return this._resolver.resolveState(this);
                };
                AbstractNode.prototype.phasedState = function () {
                    return this._resolver.alignState(this);
                };
                AbstractNode.prototype.newState = function (time) {
                    return this._resolver.newState(this, this._world, time);
                };
                AbstractNode.prototype.graph = function () {
                    return this._graph;
                };
                AbstractNode.prototype.world = function () {
                    return this._world;
                };
                AbstractNode.prototype.time = function () {
                    return this._time;
                };
                AbstractNode.prototype.id = function () {
                    return this._id;
                };
                AbstractNode.prototype.get = function (propertyName) {
                    var resolved = this._resolver.resolveState(this);
                    if (resolved != null) {
                        return resolved.get(this._resolver.stringToHash(propertyName, false));
                    }
                    return null;
                };
                AbstractNode.prototype.set = function (propertyName, propertyValue) {
                    if (typeof propertyValue === 'string' || propertyValue instanceof String) {
                        this.setProperty(propertyName, org.mwg.Type.STRING, propertyValue);
                    }
                    else if (typeof propertyValue === 'number' || propertyValue instanceof Number) {
                        if ((propertyValue % 1) != 0) {
                            this.setProperty(propertyName, org.mwg.Type.DOUBLE, propertyValue);
                        }
                        else {
                            this.setProperty(propertyName, org.mwg.Type.LONG, propertyValue);
                        }
                    }
                    else if (typeof propertyValue === 'boolean' || propertyValue instanceof Boolean) {
                        this.setProperty(propertyName, org.mwg.Type.BOOL, propertyValue);
                    }
                    else if (propertyValue instanceof Int32Array) {
                        this.setProperty(propertyName, org.mwg.Type.LONG_ARRAY, propertyValue);
                    }
                    else if (propertyValue instanceof Float64Array) {
                        this.setProperty(propertyName, org.mwg.Type.DOUBLE_ARRAY, propertyValue);
                    }
                    else {
                        throw new Error("Invalid property type: " + propertyValue + ", please use a Type listed in org.mwg.Type");
                    }
                };
                AbstractNode.prototype.forceProperty = function (propertyName, propertyType, propertyValue) {
                    var hashed = this._resolver.stringToHash(propertyName, true);
                    var preciseState = this._resolver.alignState(this);
                    if (preciseState != null) {
                        preciseState.set(hashed, propertyType, propertyValue);
                    }
                    else {
                        throw new Error(org.mwg.Constants.CACHE_MISS_ERROR);
                    }
                };
                AbstractNode.prototype.setProperty = function (propertyName, propertyType, propertyValue) {
                    var hashed = this._resolver.stringToHash(propertyName, true);
                    var unPhasedState = this._resolver.resolveState(this);
                    var isDiff = (propertyType != unPhasedState.getType(hashed));
                    if (!isDiff) {
                        isDiff = !this.isEquals(unPhasedState.get(hashed), propertyValue, propertyType);
                    }
                    if (isDiff) {
                        var preciseState = this._resolver.alignState(this);
                        if (preciseState != null) {
                            preciseState.set(hashed, propertyType, propertyValue);
                        }
                        else {
                            throw new Error(org.mwg.Constants.CACHE_MISS_ERROR);
                        }
                    }
                };
                AbstractNode.prototype.isEquals = function (obj1, obj2, type) {
                    switch (type) {
                        case org.mwg.Type.BOOL:
                            return (obj1 == obj2);
                        case org.mwg.Type.DOUBLE:
                            return (obj1 == obj2);
                        case org.mwg.Type.INT:
                            return (obj1 == obj2);
                        case org.mwg.Type.LONG:
                            return (obj1 == obj2);
                        case org.mwg.Type.STRING:
                            return (obj1 === obj2);
                        case org.mwg.Type.DOUBLE_ARRAY:
                            var obj1_ar_d = obj1;
                            var obj2_ar_d = obj2;
                            if (obj1_ar_d.length != obj2_ar_d.length) {
                                return false;
                            }
                            else {
                                for (var i = 0; i < obj1_ar_d.length; i++) {
                                    if (obj1_ar_d[i] != obj2_ar_d[i]) {
                                        return false;
                                    }
                                }
                            }
                            return true;
                        case org.mwg.Type.INT_ARRAY:
                            var obj1_ar_i = obj1;
                            var obj2_ar_i = obj2;
                            if (obj1_ar_i.length != obj2_ar_i.length) {
                                return false;
                            }
                            else {
                                for (var i = 0; i < obj1_ar_i.length; i++) {
                                    if (obj1_ar_i[i] != obj2_ar_i[i]) {
                                        return false;
                                    }
                                }
                            }
                            return true;
                        case org.mwg.Type.LONG_ARRAY:
                            var obj1_ar_l = obj1;
                            var obj2_ar_l = obj2;
                            if (obj1_ar_l.length != obj2_ar_l.length) {
                                return false;
                            }
                            else {
                                for (var i = 0; i < obj1_ar_l.length; i++) {
                                    if (obj1_ar_l[i] != obj2_ar_l[i]) {
                                        return false;
                                    }
                                }
                            }
                            return true;
                        case org.mwg.Type.RELATION:
                        case org.mwg.Type.STRING_TO_LONG_MAP:
                        case org.mwg.Type.LONG_TO_LONG_MAP:
                        case org.mwg.Type.LONG_TO_LONG_ARRAY_MAP: throw new Error("Bad API usage: set can't be used with complex type, please use getOrCreate instead.");
                        default:
                            throw new Error("Not managed type " + type);
                    }
                };
                AbstractNode.prototype.getOrCreate = function (propertyName, propertyType) {
                    var preciseState = this._resolver.alignState(this);
                    if (preciseState != null) {
                        return preciseState.getOrCreate(this._resolver.stringToHash(propertyName, true), propertyType);
                    }
                    else {
                        throw new Error(org.mwg.Constants.CACHE_MISS_ERROR);
                    }
                };
                AbstractNode.prototype.type = function (propertyName) {
                    var resolved = this._resolver.resolveState(this);
                    if (resolved != null) {
                        return resolved.getType(this._resolver.stringToHash(propertyName, false));
                    }
                    return -1;
                };
                AbstractNode.prototype.removeProperty = function (attributeName) {
                    this.setProperty(attributeName, org.mwg.Type.INT, null);
                };
                AbstractNode.prototype.rel = function (relationName, callback) {
                    this.relByIndex(this._resolver.stringToHash(relationName, false), callback);
                };
                AbstractNode.prototype.relByIndex = function (relationIndex, callback) {
                    if (callback == null) {
                        return;
                    }
                    var resolved = this._resolver.resolveState(this);
                    if (resolved != null) {
                        var relationArray = resolved.get(relationIndex);
                        if (relationArray == null || relationArray.size() == 0) {
                            callback(new Array(0));
                        }
                        else {
                            var relSize = relationArray.size();
                            var ids = new Float64Array(relSize);
                            for (var i = 0; i < relSize; i++) {
                                ids[i] = relationArray.get(i);
                            }
                            this._resolver.lookupAll(this._world, this._time, ids, function (result) {
                                {
                                    callback(result);
                                }
                            });
                        }
                    }
                    else {
                        callback(new Array(0));
                    }
                };
                AbstractNode.prototype.add = function (relationName, relatedNode) {
                    if (relatedNode != null) {
                        var preciseState = this._resolver.alignState(this);
                        var relHash = this._resolver.stringToHash(relationName, true);
                        if (preciseState != null) {
                            var relationArray = preciseState.getOrCreate(relHash, org.mwg.Type.RELATION);
                            relationArray.add(relatedNode.id());
                        }
                        else {
                            throw new Error(org.mwg.Constants.CACHE_MISS_ERROR);
                        }
                    }
                };
                AbstractNode.prototype.remove = function (relationName, relatedNode) {
                    if (relatedNode != null) {
                        var preciseState = this._resolver.alignState(this);
                        var relHash = this._resolver.stringToHash(relationName, false);
                        if (preciseState != null) {
                            var relationArray = preciseState.get(relHash);
                            if (relationArray != null) {
                                relationArray.remove(relatedNode.id());
                            }
                        }
                        else {
                            throw new Error(org.mwg.Constants.CACHE_MISS_ERROR);
                        }
                    }
                };
                AbstractNode.prototype.free = function () {
                    this._resolver.freeNode(this);
                };
                AbstractNode.prototype.timeDephasing = function () {
                    var state = this._resolver.resolveState(this);
                    if (state != null) {
                        return (this._time - state.time());
                    }
                    else {
                        throw new Error(org.mwg.Constants.CACHE_MISS_ERROR);
                    }
                };
                AbstractNode.prototype.lastModification = function () {
                    var state = this._resolver.resolveState(this);
                    if (state != null) {
                        return state.time();
                    }
                    else {
                        throw new Error(org.mwg.Constants.CACHE_MISS_ERROR);
                    }
                };
                AbstractNode.prototype.rephase = function () {
                    this._resolver.alignState(this);
                };
                AbstractNode.prototype.timepoints = function (beginningOfSearch, endOfSearch, callback) {
                    this._resolver.resolveTimepoints(this, beginningOfSearch, endOfSearch, callback);
                };
                AbstractNode.prototype.jump = function (targetTime, callback) {
                    this._resolver.lookup(this._world, targetTime, this._id, callback);
                };
                AbstractNode.prototype.findByQuery = function (query, callback) {
                    var currentNodeState = this._resolver.resolveState(this);
                    if (currentNodeState == null) {
                        throw new Error(org.mwg.Constants.CACHE_MISS_ERROR);
                    }
                    var indexName = query.indexName();
                    if (indexName == null) {
                        throw new Error("Please specify indexName in query before first use!");
                    }
                    var queryWorld = query.world();
                    if (queryWorld == org.mwg.Constants.NULL_LONG) {
                        queryWorld = this.world();
                    }
                    var queryTime = query.time();
                    if (queryTime == org.mwg.Constants.NULL_LONG) {
                        queryTime = this.time();
                    }
                    var indexMap = currentNodeState.get(this._resolver.stringToHash(indexName, false));
                    if (indexMap != null) {
                        var selfPointer_1 = this;
                        var foundIds_1 = indexMap.get(query.hash());
                        if (foundIds_1 == null) {
                            callback(new Array(0));
                            return;
                        }
                        selfPointer_1._resolver.lookupAll(queryWorld, queryTime, foundIds_1, function (resolved) {
                            {
                                var resultSet = new Array(foundIds_1.length);
                                var resultSetIndex = 0;
                                for (var i = 0; i < resultSet.length; i++) {
                                    var resolvedNode = resolved[i];
                                    if (resolvedNode != null) {
                                        var resolvedState = selfPointer_1._resolver.resolveState(resolvedNode);
                                        var exact = true;
                                        for (var j = 0; j < query.attributes().length; j++) {
                                            var obj = resolvedState.get(query.attributes()[j]);
                                            if (query.values()[j] == null) {
                                                if (obj != null) {
                                                    exact = false;
                                                    break;
                                                }
                                            }
                                            else {
                                                if (obj == null) {
                                                    exact = false;
                                                    break;
                                                }
                                                else {
                                                    if (obj instanceof Float64Array) {
                                                        if (query.values()[j] instanceof Float64Array) {
                                                            if (!org.mwg.Constants.longArrayEquals(query.values()[j], obj)) {
                                                                exact = false;
                                                                break;
                                                            }
                                                        }
                                                        else {
                                                            exact = false;
                                                            break;
                                                        }
                                                    }
                                                    else {
                                                        if (!org.mwg.Constants.equals(query.values()[j].toString(), obj.toString())) {
                                                            exact = false;
                                                            break;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        if (exact) {
                                            resultSet[resultSetIndex] = resolvedNode;
                                            resultSetIndex++;
                                        }
                                    }
                                }
                                if (resultSet.length == resultSetIndex) {
                                    callback(resultSet);
                                }
                                else {
                                    var trimmedResultSet = new Array(resultSetIndex);
                                    java.lang.System.arraycopy(resultSet, 0, trimmedResultSet, 0, resultSetIndex);
                                    callback(trimmedResultSet);
                                }
                            }
                        });
                    }
                    else {
                        callback(new Array(0));
                    }
                };
                AbstractNode.prototype.find = function (indexName, query, callback) {
                    var queryObj = this._graph.newQuery();
                    queryObj.setWorld(this.world());
                    queryObj.setTime(this.time());
                    queryObj.setIndexName(indexName);
                    queryObj.parse(query);
                    this.findByQuery(queryObj, callback);
                };
                AbstractNode.prototype.findAll = function (indexName, callback) {
                    var currentNodeState = this._resolver.resolveState(this);
                    if (currentNodeState == null) {
                        throw new Error(org.mwg.Constants.CACHE_MISS_ERROR);
                    }
                    var indexMap = currentNodeState.get(this._resolver.stringToHash(indexName, false));
                    if (indexMap != null) {
                        var ids_1 = new Float64Array(indexMap.size());
                        var idIndex_1 = new Int32Array([0]);
                        indexMap.each(function (hash, nodeId) {
                            {
                                ids_1[idIndex_1[0]] = nodeId;
                                idIndex_1[0]++;
                            }
                        });
                        this._resolver.lookupAll(this.world(), this.time(), ids_1, function (result) {
                            {
                                callback(result);
                            }
                        });
                    }
                    else {
                        callback(new Array(0));
                    }
                };
                AbstractNode.prototype.index = function (indexName, nodeToIndex, flatKeyAttributes, callback) {
                    var keyAttributes = flatKeyAttributes.split(org.mwg.Constants.QUERY_SEP + "");
                    var hashName = this._resolver.stringToHash(indexName, true);
                    var flatQuery = this._graph.newQuery();
                    var toIndexNodeState = this._resolver.resolveState(nodeToIndex);
                    for (var i = 0; i < keyAttributes.length; i++) {
                        var attKey = keyAttributes[i];
                        var attValue = toIndexNodeState.getFromKey(attKey);
                        if (attValue != null) {
                            flatQuery.add(attKey, attValue.toString());
                        }
                        else {
                            flatQuery.add(attKey, null);
                        }
                    }
                    var alreadyIndexed = false;
                    var previousState = this._resolver.resolveState(this);
                    if (previousState != null) {
                        var previousMap = previousState.get(hashName);
                        if (previousMap != null) {
                            alreadyIndexed = previousMap.contains(flatQuery.hash(), nodeToIndex.id());
                        }
                    }
                    if (!alreadyIndexed) {
                        var currentNodeState = this._resolver.alignState(this);
                        if (currentNodeState == null) {
                            throw new Error(org.mwg.Constants.CACHE_MISS_ERROR);
                        }
                        var indexMap = currentNodeState.getOrCreate(hashName, org.mwg.Type.LONG_TO_LONG_ARRAY_MAP);
                        indexMap.put(flatQuery.hash(), nodeToIndex.id());
                    }
                    if (org.mwg.Constants.isDefined(callback)) {
                        callback(true);
                    }
                };
                AbstractNode.prototype.unindex = function (indexName, nodeToIndex, flatKeyAttributes, callback) {
                    var keyAttributes = flatKeyAttributes.split(org.mwg.Constants.QUERY_SEP + "");
                    var currentNodeState = this._resolver.alignState(this);
                    if (currentNodeState == null) {
                        throw new Error(org.mwg.Constants.CACHE_MISS_ERROR);
                    }
                    var indexMap = currentNodeState.get(this._resolver.stringToHash(indexName, false));
                    if (indexMap != null) {
                        var flatQuery = this._graph.newQuery();
                        var toIndexNodeState = this._resolver.resolveState(nodeToIndex);
                        for (var i = 0; i < keyAttributes.length; i++) {
                            var attKey = keyAttributes[i];
                            var attValue = toIndexNodeState.getFromKey(attKey);
                            if (attValue != null) {
                                flatQuery.add(attKey, attValue.toString());
                            }
                            else {
                                flatQuery.add(attKey, null);
                            }
                        }
                        indexMap.remove(flatQuery.hash(), nodeToIndex.id());
                    }
                    if (org.mwg.Constants.isDefined(callback)) {
                        callback(true);
                    }
                };
                AbstractNode.prototype.isNaN = function (toTest) {
                    return isNaN(toTest);
                };
                AbstractNode.prototype.toString = function () {
                    var _this = this;
                    var builder = new java.lang.StringBuilder();
                    var isFirst = [true];
                    builder.append("{\"world\":");
                    builder.append(this.world());
                    builder.append(",\"time\":");
                    builder.append(this.time());
                    builder.append(",\"id\":");
                    builder.append(this.id());
                    var state = this._resolver.resolveState(this);
                    if (state != null) {
                        state.each(function (attributeKey, elemType, elem) {
                            {
                                if (elem != null) {
                                    var resolveName = _this._resolver.hashToString(attributeKey);
                                    if (resolveName == null) {
                                        resolveName = attributeKey + "";
                                    }
                                    switch (elemType) {
                                        case org.mwg.Type.BOOL: {
                                            builder.append(",\"");
                                            builder.append(resolveName);
                                            builder.append("\":");
                                            if (elem) {
                                                builder.append("0");
                                            }
                                            else {
                                                builder.append("1");
                                            }
                                            break;
                                        }
                                        case org.mwg.Type.STRING: {
                                            builder.append(",\"");
                                            builder.append(resolveName);
                                            builder.append("\":");
                                            builder.append("\"");
                                            builder.append(elem);
                                            builder.append("\"");
                                            break;
                                        }
                                        case org.mwg.Type.LONG: {
                                            builder.append(",\"");
                                            builder.append(resolveName);
                                            builder.append("\":");
                                            builder.append(elem);
                                            break;
                                        }
                                        case org.mwg.Type.INT: {
                                            builder.append(",\"");
                                            builder.append(resolveName);
                                            builder.append("\":");
                                            builder.append(elem);
                                            break;
                                        }
                                        case org.mwg.Type.DOUBLE: {
                                            if (!_this.isNaN(elem)) {
                                                builder.append(",\"");
                                                builder.append(resolveName);
                                                builder.append("\":");
                                                builder.append(elem);
                                            }
                                            break;
                                        }
                                        case org.mwg.Type.DOUBLE_ARRAY: {
                                            builder.append(",\"");
                                            builder.append(resolveName);
                                            builder.append("\":");
                                            builder.append("[");
                                            var castedArr = elem;
                                            for (var j = 0; j < castedArr.length; j++) {
                                                if (j != 0) {
                                                    builder.append(",");
                                                }
                                                builder.append(castedArr[j]);
                                            }
                                            builder.append("]");
                                            break;
                                        }
                                        case org.mwg.Type.RELATION:
                                            builder.append(",\"");
                                            builder.append(resolveName);
                                            builder.append("\":");
                                            builder.append("[");
                                            var castedRelArr = elem;
                                            for (var j = 0; j < castedRelArr.size(); j++) {
                                                if (j != 0) {
                                                    builder.append(",");
                                                }
                                                builder.append(castedRelArr.get(j));
                                            }
                                            builder.append("]");
                                            break;
                                        case org.mwg.Type.LONG_ARRAY: {
                                            builder.append(",\"");
                                            builder.append(resolveName);
                                            builder.append("\":");
                                            builder.append("[");
                                            var castedArr2 = elem;
                                            for (var j = 0; j < castedArr2.length; j++) {
                                                if (j != 0) {
                                                    builder.append(",");
                                                }
                                                builder.append(castedArr2[j]);
                                            }
                                            builder.append("]");
                                            break;
                                        }
                                        case org.mwg.Type.INT_ARRAY: {
                                            builder.append(",\"");
                                            builder.append(resolveName);
                                            builder.append("\":");
                                            builder.append("[");
                                            var castedArr3 = elem;
                                            for (var j = 0; j < castedArr3.length; j++) {
                                                if (j != 0) {
                                                    builder.append(",");
                                                }
                                                builder.append(castedArr3[j]);
                                            }
                                            builder.append("]");
                                            break;
                                        }
                                        case org.mwg.Type.LONG_TO_LONG_MAP: {
                                            builder.append(",\"");
                                            builder.append(resolveName);
                                            builder.append("\":");
                                            builder.append("{");
                                            var castedMapL2L = elem;
                                            isFirst[0] = true;
                                            castedMapL2L.each(function (key, value) {
                                                {
                                                    if (!isFirst[0]) {
                                                        builder.append(",");
                                                    }
                                                    else {
                                                        isFirst[0] = false;
                                                    }
                                                    builder.append("\"");
                                                    builder.append(key);
                                                    builder.append("\":");
                                                    builder.append(value);
                                                }
                                            });
                                            builder.append("}");
                                            break;
                                        }
                                        case org.mwg.Type.LONG_TO_LONG_ARRAY_MAP: {
                                            builder.append(",\"");
                                            builder.append(resolveName);
                                            builder.append("\":");
                                            builder.append("{");
                                            var castedMapL2LA = elem;
                                            isFirst[0] = true;
                                            var keys_1 = new java.util.HashSet();
                                            castedMapL2LA.each(function (key, value) {
                                                {
                                                    keys_1.add(key);
                                                }
                                            });
                                            var flatKeys = keys_1.toArray(new Array(keys_1.size()));
                                            for (var i = 0; i < flatKeys.length; i++) {
                                                var values = castedMapL2LA.get(flatKeys[i]);
                                                if (!isFirst[0]) {
                                                    builder.append(",");
                                                }
                                                else {
                                                    isFirst[0] = false;
                                                }
                                                builder.append("\"");
                                                builder.append(flatKeys[i]);
                                                builder.append("\":[");
                                                for (var j = 0; j < values.length; j++) {
                                                    if (j != 0) {
                                                        builder.append(",");
                                                    }
                                                    builder.append(values[j]);
                                                }
                                                builder.append("]");
                                            }
                                            builder.append("}");
                                            break;
                                        }
                                        case org.mwg.Type.STRING_TO_LONG_MAP: {
                                            builder.append(",\"");
                                            builder.append(resolveName);
                                            builder.append("\":");
                                            builder.append("{");
                                            var castedMapS2L = elem;
                                            isFirst[0] = true;
                                            castedMapS2L.each(function (key, value) {
                                                {
                                                    if (!isFirst[0]) {
                                                        builder.append(",");
                                                    }
                                                    else {
                                                        isFirst[0] = false;
                                                    }
                                                    builder.append("\"");
                                                    builder.append(key);
                                                    builder.append("\":");
                                                    builder.append(value);
                                                }
                                            });
                                            builder.append("}");
                                            break;
                                        }
                                    }
                                }
                            }
                        });
                        builder.append("}");
                    }
                    return builder.toString();
                };
                AbstractNode.prototype.getOrCreateRel = function (propertyName) {
                    return this.getOrCreate(propertyName, org.mwg.Type.RELATION);
                };
                AbstractNode.prototype.getByIndex = function (propIndex) {
                    return this._resolver.resolveState(this).get(propIndex);
                };
                AbstractNode.prototype.setPropertyByIndex = function (propIndex, propertyType, propertyValue) {
                    this._resolver.alignState(this).set(propIndex, propertyType, propertyValue);
                };
                return AbstractNode;
            }());
            plugin.AbstractNode = AbstractNode;
            var AbstractPlugin = (function () {
                function AbstractPlugin() {
                    this._nodeTypes = new java.util.HashMap();
                    this._taskActions = new java.util.HashMap();
                }
                AbstractPlugin.prototype.declareNodeType = function (name, factory) {
                    this._nodeTypes.put(name, factory);
                    return this;
                };
                AbstractPlugin.prototype.declareTaskAction = function (name, factory) {
                    this._taskActions.put(name, factory);
                    return this;
                };
                AbstractPlugin.prototype.declareMemoryFactory = function (factory) {
                    this._memoryFactory = factory;
                    return this;
                };
                AbstractPlugin.prototype.declareResolverFactory = function (factory) {
                    this._resolverFactory = factory;
                    return this;
                };
                AbstractPlugin.prototype.hookFactory = function () {
                    return this._hookFactory;
                };
                AbstractPlugin.prototype.declareTaskHookFactory = function (factory) {
                    this._hookFactory = factory;
                    return this;
                };
                AbstractPlugin.prototype.nodeTypes = function () {
                    return this._nodeTypes.keySet().toArray(new Array(this._nodeTypes.size()));
                };
                AbstractPlugin.prototype.nodeType = function (nodeTypeName) {
                    return this._nodeTypes.get(nodeTypeName);
                };
                AbstractPlugin.prototype.taskActionTypes = function () {
                    return this._taskActions.keySet().toArray(new Array(this._taskActions.size()));
                };
                AbstractPlugin.prototype.taskActionType = function (taskTypeName) {
                    return this._taskActions.get(taskTypeName);
                };
                AbstractPlugin.prototype.memoryFactory = function () {
                    return this._memoryFactory;
                };
                AbstractPlugin.prototype.resolverFactory = function () {
                    return this._resolverFactory;
                };
                return AbstractPlugin;
            }());
            plugin.AbstractPlugin = AbstractPlugin;
            var AbstractTaskAction = (function () {
                function AbstractTaskAction() {
                    this._next = null;
                }
                AbstractTaskAction.prototype.setNext = function (p_next) {
                    this._next = p_next;
                };
                AbstractTaskAction.prototype.next = function () {
                    return this._next;
                };
                return AbstractTaskAction;
            }());
            plugin.AbstractTaskAction = AbstractTaskAction;
            var SchedulerAffinity = (function () {
                function SchedulerAffinity() {
                }
                SchedulerAffinity.SAME_THREAD = 0;
                SchedulerAffinity.ANY_LOCAL_THREAD = 1;
                SchedulerAffinity.OTHER_LOCAL_THREAD = 2;
                SchedulerAffinity.ANY_REMOTE_THREAD = 3;
                return SchedulerAffinity;
            }());
            plugin.SchedulerAffinity = SchedulerAffinity;
        })(plugin = mwg.plugin || (mwg.plugin = {}));
        var task;
        (function (task) {
            var Actions = (function () {
                function Actions() {
                }
                Actions.newTask = function () {
                    if (org.mwg.task.Actions._internalBuilder == null) {
                        org.mwg.task.Actions._internalBuilder = new org.mwg.core.Builder();
                    }
                    return org.mwg.task.Actions._internalBuilder.newTask();
                };
                Actions.setWorld = function (variableName) {
                    return org.mwg.task.Actions.newTask().setWorld(variableName);
                };
                Actions.setTime = function (variableName) {
                    return org.mwg.task.Actions.newTask().setTime(variableName);
                };
                Actions.then = function (action) {
                    return org.mwg.task.Actions.newTask().then(action);
                };
                Actions.inject = function (input) {
                    return org.mwg.task.Actions.newTask().inject(input);
                };
                Actions.fromVar = function (variableName) {
                    return org.mwg.task.Actions.newTask().fromVar(variableName);
                };
                Actions.fromVarAt = function (variableName, index) {
                    return org.mwg.task.Actions.newTask().fromVarAt(variableName, index);
                };
                Actions.fromIndexAll = function (indexName) {
                    return org.mwg.task.Actions.newTask().fromIndexAll(indexName);
                };
                Actions.fromIndex = function (indexName, query) {
                    return org.mwg.task.Actions.newTask().fromIndex(indexName, query);
                };
                Actions.indexNode = function (indexName, flatKeyAttributes) {
                    return org.mwg.task.Actions.newTask().indexNode(indexName, flatKeyAttributes);
                };
                Actions.unindexNode = function (indexName, flatKeyAttributes) {
                    return org.mwg.task.Actions.newTask().unindexNode(indexName, flatKeyAttributes);
                };
                Actions.localIndex = function (indexedRelation, flatKeyAttributes, varNodeToAdd) {
                    return org.mwg.task.Actions.newTask().localIndex(indexedRelation, flatKeyAttributes, varNodeToAdd);
                };
                Actions.localUnindex = function (indexedRelation, flatKeyAttributes, varNodeToAdd) {
                    return org.mwg.task.Actions.newTask().localUnindex(indexedRelation, flatKeyAttributes, varNodeToAdd);
                };
                Actions.indexesNames = function () {
                    return org.mwg.task.Actions.newTask().indexesNames();
                };
                Actions.parse = function (flatTask) {
                    return org.mwg.task.Actions.newTask().parse(flatTask);
                };
                Actions.asGlobalVar = function (variableName) {
                    return org.mwg.task.Actions.newTask().asGlobalVar(variableName);
                };
                Actions.addToGlobalVar = function (variableName) {
                    return org.mwg.task.Actions.newTask().addToGlobalVar(variableName);
                };
                Actions.asVar = function (variableName) {
                    return org.mwg.task.Actions.newTask().asVar(variableName);
                };
                Actions.defineVar = function (variableName) {
                    return org.mwg.task.Actions.newTask().defineVar(variableName);
                };
                Actions.addToVar = function (variableName) {
                    return org.mwg.task.Actions.newTask().addToVar(variableName);
                };
                Actions.map = function (mapFunction) {
                    return org.mwg.task.Actions.newTask().map(mapFunction);
                };
                Actions.selectWith = function (name, pattern) {
                    return org.mwg.task.Actions.newTask().selectWith(name, pattern);
                };
                Actions.selectWithout = function (name, pattern) {
                    return org.mwg.task.Actions.newTask().selectWithout(name, pattern);
                };
                Actions.select = function (filterFunction) {
                    return org.mwg.task.Actions.newTask().select(filterFunction);
                };
                Actions.selectObject = function (filterFunction) {
                    return org.mwg.task.Actions.newTask().selectObject(filterFunction);
                };
                Actions.traverse = function (relationName) {
                    return org.mwg.task.Actions.newTask().traverse(relationName);
                };
                Actions.get = function (name) {
                    return org.mwg.task.Actions.newTask().get(name);
                };
                Actions.traverseIndex = function (indexName) {
                    var queryParams = [];
                    for (var _i = 1; _i < arguments.length; _i++) {
                        queryParams[_i - 1] = arguments[_i];
                    }
                    var t = (_a = org.mwg.task.Actions.newTask()).traverseIndex.apply(_a, [indexName].concat(queryParams));
                    return t;
                    var _a;
                };
                Actions.traverseOrKeep = function (relationName) {
                    return org.mwg.task.Actions.newTask().traverseOrKeep(relationName);
                };
                Actions.traverseIndexAll = function (indexName) {
                    return org.mwg.task.Actions.newTask().traverseIndexAll(indexName);
                };
                Actions.loop = function (from, to, subTask) {
                    return org.mwg.task.Actions.newTask().loop(from, to, subTask);
                };
                Actions.loopPar = function (from, to, subTask) {
                    return org.mwg.task.Actions.newTask().loopPar(from, to, subTask);
                };
                Actions.print = function (name) {
                    return org.mwg.task.Actions.newTask().print(name);
                };
                Actions.setProperty = function (propertyName, propertyType, variableNameToSet) {
                    return org.mwg.task.Actions.newTask().setProperty(propertyName, propertyType, variableNameToSet);
                };
                Actions.selectWhere = function (subTask) {
                    return org.mwg.task.Actions.newTask().selectWhere(subTask);
                };
                Actions.foreach = function (subTask) {
                    return org.mwg.task.Actions.newTask().foreach(subTask);
                };
                Actions.foreachPar = function (subTask) {
                    return org.mwg.task.Actions.newTask().foreachPar(subTask);
                };
                Actions.flatmap = function (subTask) {
                    return org.mwg.task.Actions.newTask().flatmap(subTask);
                };
                Actions.flatmapPar = function (subTask) {
                    return org.mwg.task.Actions.newTask().flatmapPar(subTask);
                };
                Actions.math = function (expression) {
                    return org.mwg.task.Actions.newTask().math(expression);
                };
                Actions.action = function (name, params) {
                    return org.mwg.task.Actions.newTask().action(name, params);
                };
                Actions.remove = function (relationName, variableNameToRemove) {
                    return org.mwg.task.Actions.newTask().remove(relationName, variableNameToRemove);
                };
                Actions.add = function (relationName, variableNameToAdd) {
                    return org.mwg.task.Actions.newTask().add(relationName, variableNameToAdd);
                };
                Actions.properties = function () {
                    return org.mwg.task.Actions.newTask().properties();
                };
                Actions.propertiesWithTypes = function (filter) {
                    return org.mwg.task.Actions.newTask().propertiesWithTypes(filter);
                };
                Actions.jump = function (time) {
                    return org.mwg.task.Actions.newTask().jump(time);
                };
                Actions.removeProperty = function (propertyName) {
                    return org.mwg.task.Actions.newTask().removeProperty(propertyName);
                };
                Actions.newNode = function () {
                    return org.mwg.task.Actions.newTask().newNode();
                };
                Actions.newTypedNode = function (nodeType) {
                    return org.mwg.task.Actions.newTask().newTypedNode(nodeType);
                };
                Actions.save = function () {
                    return org.mwg.task.Actions.newTask().save();
                };
                Actions.ifThen = function (cond, then) {
                    return org.mwg.task.Actions.newTask().ifThen(cond, then);
                };
                Actions.ifThenElse = function (cond, thenSub, elseSub) {
                    return org.mwg.task.Actions.newTask().ifThenElse(cond, thenSub, elseSub);
                };
                Actions.whileDo = function (cond, then) {
                    return org.mwg.task.Actions.newTask().whileDo(cond, then);
                };
                Actions.doWhile = function (then, cond) {
                    return org.mwg.task.Actions.newTask().doWhile(then, cond);
                };
                Actions.split = function (splitPattern) {
                    return org.mwg.task.Actions.newTask().split(splitPattern);
                };
                Actions.lookup = function (nodeId) {
                    return org.mwg.task.Actions.newTask().lookup(nodeId);
                };
                Actions.hook = function (fact) {
                    return org.mwg.task.Actions.newTask().hook(fact);
                };
                Actions.clear = function () {
                    return org.mwg.task.Actions.newTask().clear();
                };
                Actions.subTask = function (subTask) {
                    return org.mwg.task.Actions.newTask().subTask(subTask);
                };
                Actions.isolate = function (subTask) {
                    return org.mwg.task.Actions.newTask().isolate(subTask);
                };
                Actions.subTasks = function (subTasks) {
                    return org.mwg.task.Actions.newTask().subTasks(subTasks);
                };
                Actions.subTasksPar = function (subTasks) {
                    return org.mwg.task.Actions.newTask().subTasksPar(subTasks);
                };
                Actions.cond = function (mathExpression) {
                    return org.mwg.task.Actions.newTask().mathConditional(mathExpression);
                };
                Actions._internalBuilder = null;
                return Actions;
            }());
            task.Actions = Actions;
        })(task = mwg.task || (mwg.task = {}));
        var utility;
        (function (utility) {
            var Base64 = (function () {
                function Base64() {
                }
                Base64.encodeLongToBuffer = function (l, buffer) {
                    var empty = true;
                    var tmp = l;
                    if (l < 0) {
                        tmp = -tmp;
                    }
                    for (var i = 47; i >= 5; i -= 6) {
                        if (!(empty && ((tmp / Base64.powTwo[i]) & 0x3F) == 0)) {
                            empty = false;
                            buffer.write(Base64.dictionary[(tmp / Base64.powTwo[i]) & 0x3F]);
                        }
                    }
                    buffer.write(Base64.dictionary[(tmp & 0x1F) * 2 + (l < 0 ? 1 : 0)]);
                };
                Base64.encodeIntToBuffer = function (l, buffer) {
                    var empty = true;
                    var tmp = l;
                    if (l < 0) {
                        tmp = -tmp;
                    }
                    for (var i = 29; i >= 5; i -= 6) {
                        if (!(empty && ((tmp / Base64.powTwo[i]) & 0x3F) == 0)) {
                            empty = false;
                            buffer.write(Base64.dictionary[(tmp / Base64.powTwo[i]) & 0x3F]);
                        }
                    }
                    buffer.write(Base64.dictionary[(tmp & 0x1F) * 2 + (l < 0 ? 1 : 0)]);
                };
                Base64.decodeToLong = function (s) {
                    return Base64.decodeToLongWithBounds(s, 0, s.length());
                };
                Base64.decodeToLongWithBounds = function (s, offsetBegin, offsetEnd) {
                    var result = Long.ZERO;
                    result = result.add(Base64.longIndexes[Base64.dictionary.indexOf(s.read((offsetEnd - 1))) & 0xFF].shiftRightUnsigned(1));
                    for (var i = 1; i < (offsetEnd - offsetBegin); i++) {
                        result = result.add(Base64.longIndexes[Base64.dictionary.indexOf(s.read((offsetEnd - 1) - i)) & 0xFF].shiftLeft((6 * i) - 1));
                    }
                    if (((Base64.dictionary.indexOf(s.read((offsetEnd - 1))) & 0xFF) & 0x1) != 0) {
                        result = result.mul(-1);
                    }
                    return result.toNumber();
                };
                Base64.decodeToInt = function (s) {
                    return Base64.decodeToIntWithBounds(s, 0, s.length());
                };
                Base64.decodeToIntWithBounds = function (s, offsetBegin, offsetEnd) {
                    var result = 0;
                    result += (Base64.dictionary.indexOf(s.read((offsetEnd - 1))) & 0xFF) / 2;
                    for (var i = 1; i < (offsetEnd - offsetBegin); i++) {
                        result += (Base64.dictionary.indexOf(s.read((offsetEnd - 1) - i)) & 0xFF) * Base64.powTwo[(6 * i) - 1];
                    }
                    if (((Base64.dictionary.indexOf(s.read((offsetEnd - 1))) & 0xFF) & 0x1) != 0) {
                        result = -result;
                    }
                    return result;
                };
                Base64.encodeDoubleToBuffer = function (d, buffer) {
                    var result = [];
                    var floatArr = new Float64Array(1);
                    var bytes = new Uint8Array(floatArr.buffer);
                    floatArr[0] = d;
                    var exponent = (((bytes[7] & 0x7f) * 16) | bytes[6] / 16) - 0x3ff;
                    var signAndExp = (((bytes[7] / 128) & 0x1) * 2048) + (exponent + 1023);
                    //encode sign + exp
                    result.push(Base64.dictionary[(signAndExp / 64) & 0x3F]);
                    result.push(Base64.dictionary[signAndExp & 0x3F]);
                    result.push(Base64.dictionary[bytes[6] & 0x0F]);
                    result.push(Base64.dictionary[(bytes[5] / 4) & 0x3F]);
                    result.push(Base64.dictionary[((bytes[5] & 0x3) * 16) | (bytes[4] / 16)]);
                    result.push(Base64.dictionary[((bytes[4] & 0x0F) * 4) | (bytes[3] / 64)]);
                    result.push(Base64.dictionary[(bytes[3] & 0x3F)]);
                    result.push(Base64.dictionary[(bytes[2] / 4) & 0x3F]);
                    result.push(Base64.dictionary[((bytes[2] & 0x3) * 16) | (bytes[1] / 16)]);
                    result.push(Base64.dictionary[((bytes[1] & 0x0F) * 4) | (bytes[0] / 64)]);
                    result.push(Base64.dictionary[(bytes[0] & 0x3F)]);
                    var indexMax = result.length;
                    while (indexMax >= 3 && result[i] == 65) {
                        indexMax--;
                    }
                    for (var i = 0; i < indexMax; i++) {
                        buffer.write(result[i]);
                    }
                };
                Base64.decodeToDouble = function (s) {
                    return Base64.decodeToDoubleWithBounds(s, 0, s.length());
                };
                Base64.decodeToDoubleWithBounds = function (s, offsetBegin, offsetEnd) {
                    var signAndExp = ((Base64.dictionary.indexOf(s.read(offsetBegin)) & 0xFF) * 64) + (Base64.dictionary.indexOf(s.read(offsetBegin + 1)) & 0xFF);
                    var sign = ((signAndExp & 0x800) != 0 ? -1 : 1);
                    var exp = signAndExp & 0x7FF;
                    //Mantisse
                    var mantissaBits = 0;
                    for (var i = 2; i < (offsetEnd - offsetBegin); i++) {
                        mantissaBits += (Base64.dictionary.indexOf(s.read(offsetBegin + i)) & 0xFF) * Base64.powTwo[48 - (6 * (i - 2))];
                    }
                    return (exp != 0) ? sign * Math.pow(2, exp - 1023) * (1 + (mantissaBits / Math.pow(2, 52))) : sign * Math.pow(2, -1022) * (0 + (mantissaBits / Math.pow(2, 52)));
                };
                Base64.encodeBoolArrayToBuffer = function (boolArr, buffer) {
                    var tmpVal = 0;
                    for (var i = 0; i < boolArr.length; i++) {
                        tmpVal = tmpVal | ((boolArr[i] ? 1 : 0) * Base64.powTwo[i % 6]);
                        if (i % 6 == 5 || i == boolArr.length - 1) {
                            buffer.write(Base64.dictionary[tmpVal]);
                            tmpVal = 0;
                        }
                    }
                };
                Base64.decodeBoolArray = function (s, arraySize) {
                    return Base64.decodeToBoolArrayWithBounds(s, 0, s.length(), arraySize);
                };
                Base64.decodeToBoolArrayWithBounds = function (s, offsetBegin, offsetEnd, arraySize) {
                    var resultTmp = [];
                    for (var i = 0; i < (offsetEnd - offsetBegin); i++) {
                        var bitarray = Base64.dictionary.indexOf(s.read(offsetBegin + i)) & 0xFF;
                        for (var bit_i = 0; bit_i < 6; bit_i++) {
                            if ((6 * i) + bit_i < arraySize) {
                                resultTmp[(6 * i) + bit_i] = (bitarray & (1 * Base64.powTwo[bit_i])) != 0;
                            }
                            else {
                                break;
                            }
                        }
                    }
                    return resultTmp;
                };
                Base64.encodeStringToBuffer = function (s, buffer) {
                    var sLength = s.length;
                    var currentSourceChar;
                    var currentEncodedChar = 0;
                    var freeBitsInCurrentChar = 6;
                    for (var charIdx = 0; charIdx < sLength; charIdx++) {
                        currentSourceChar = s.charCodeAt(charIdx);
                        if (freeBitsInCurrentChar == 6) {
                            buffer.write(Base64.dictionary[(currentSourceChar / 4) & 0x3F]);
                            currentEncodedChar = (currentSourceChar & 0x3) * 16;
                            freeBitsInCurrentChar = 4;
                        }
                        else if (freeBitsInCurrentChar == 4) {
                            buffer.write(Base64.dictionary[(currentEncodedChar | ((currentSourceChar / 16) & 0xF)) & 0x3F]);
                            currentEncodedChar = (currentSourceChar & 0xF) * 4;
                            freeBitsInCurrentChar = 2;
                        }
                        else if (freeBitsInCurrentChar == 2) {
                            buffer.write(Base64.dictionary[(currentEncodedChar | ((currentSourceChar / 64) & 0x3)) & 0x3F]);
                            buffer.write(Base64.dictionary[currentSourceChar & 0x3F]);
                            freeBitsInCurrentChar = 6;
                        }
                    }
                    if (freeBitsInCurrentChar != 6) {
                        buffer.write(Base64.dictionary[currentEncodedChar]);
                    }
                };
                Base64.decodeString = function (s) {
                    return Base64.decodeToStringWithBounds(s, 0, s.length());
                };
                Base64.decodeToStringWithBounds = function (s, offsetBegin, offsetEnd) {
                    var result = "";
                    var currentSourceChar;
                    var currentDecodedChar = 0;
                    var freeBitsInCurrentChar = 8;
                    for (var charIdx = offsetBegin; charIdx < offsetEnd; charIdx++) {
                        currentSourceChar = Base64.dictionary.indexOf(s.read(charIdx));
                        if (freeBitsInCurrentChar == 8) {
                            currentDecodedChar = currentSourceChar * 4;
                            freeBitsInCurrentChar = 2;
                        }
                        else if (freeBitsInCurrentChar == 2) {
                            result += String.fromCharCode(currentDecodedChar | (currentSourceChar / 16));
                            currentDecodedChar = (currentSourceChar & 0xF) * 16;
                            freeBitsInCurrentChar = 4;
                        }
                        else if (freeBitsInCurrentChar == 4) {
                            result += String.fromCharCode(currentDecodedChar | (currentSourceChar / 4));
                            currentDecodedChar = (currentSourceChar & 0x3) * 64;
                            freeBitsInCurrentChar = 6;
                        }
                        else if (freeBitsInCurrentChar == 6) {
                            result += String.fromCharCode(currentDecodedChar | currentSourceChar);
                            freeBitsInCurrentChar = 8;
                        }
                    }
                    return result;
                };
                Base64.dictionary = ['A'.charCodeAt(0), 'B'.charCodeAt(0), 'C'.charCodeAt(0), 'D'.charCodeAt(0), 'E'.charCodeAt(0), 'F'.charCodeAt(0), 'G'.charCodeAt(0), 'H'.charCodeAt(0), 'I'.charCodeAt(0), 'J'.charCodeAt(0), 'K'.charCodeAt(0), 'L'.charCodeAt(0), 'M'.charCodeAt(0), 'N'.charCodeAt(0), 'O'.charCodeAt(0), 'P'.charCodeAt(0), 'Q'.charCodeAt(0), 'R'.charCodeAt(0), 'S'.charCodeAt(0), 'T'.charCodeAt(0), 'U'.charCodeAt(0), 'V'.charCodeAt(0), 'W'.charCodeAt(0), 'X'.charCodeAt(0), 'Y'.charCodeAt(0), 'Z'.charCodeAt(0), 'a'.charCodeAt(0), 'b'.charCodeAt(0), 'c'.charCodeAt(0), 'd'.charCodeAt(0), 'e'.charCodeAt(0), 'f'.charCodeAt(0), 'g'.charCodeAt(0), 'h'.charCodeAt(0), 'i'.charCodeAt(0), 'j'.charCodeAt(0), 'k'.charCodeAt(0), 'l'.charCodeAt(0), 'm'.charCodeAt(0), 'n'.charCodeAt(0), 'o'.charCodeAt(0), 'p'.charCodeAt(0), 'q'.charCodeAt(0), 'r'.charCodeAt(0), 's'.charCodeAt(0), 't'.charCodeAt(0), 'u'.charCodeAt(0), 'v'.charCodeAt(0), 'w'.charCodeAt(0), 'x'.charCodeAt(0), 'y'.charCodeAt(0), 'z'.charCodeAt(0), '0'.charCodeAt(0), '1'.charCodeAt(0), '2'.charCodeAt(0), '3'.charCodeAt(0), '4'.charCodeAt(0), '5'.charCodeAt(0), '6'.charCodeAt(0), '7'.charCodeAt(0), '8'.charCodeAt(0), '9'.charCodeAt(0), '+'.charCodeAt(0), '/'.charCodeAt(0)];
                Base64.powTwo = { 0: 1, 1: 2, 2: 4, 3: 8, 4: 16, 5: 32, 6: 64, 7: 128, 8: 256, 9: 512, 10: 1024, 11: 2048, 12: 4096, 13: 8192, 14: 16384, 15: 32768, 16: 65536, 17: 131072, 18: 262144, 19: 524288, 20: 1048576, 21: 2097152, 22: 4194304, 23: 8388608, 24: 16777216, 25: 33554432, 26: 67108864, 27: 134217728, 28: 268435456, 29: 536870912, 30: 1073741824, 31: 2147483648, 32: 4294967296, 33: 8589934592, 34: 17179869184, 35: 34359738368, 36: 68719476736, 37: 137438953472, 38: 274877906944, 39: 549755813888, 40: 1099511627776, 41: 2199023255552, 42: 4398046511104, 43: 8796093022208, 44: 17592186044416, 45: 35184372088832, 46: 70368744177664, 47: 140737488355328, 48: 281474976710656, 49: 562949953421312, 50: 1125899906842624, 51: 2251799813685248, 52: 4503599627370496, 53: 9007199254740992 };
                Base64.longIndexes = [Long.fromNumber(0), Long.fromNumber(1), Long.fromNumber(2), Long.fromNumber(3), Long.fromNumber(4), Long.fromNumber(5), Long.fromNumber(6), Long.fromNumber(7), Long.fromNumber(8), Long.fromNumber(9), Long.fromNumber(10), Long.fromNumber(11), Long.fromNumber(12), Long.fromNumber(13), Long.fromNumber(14), Long.fromNumber(15), Long.fromNumber(16), Long.fromNumber(17), Long.fromNumber(18), Long.fromNumber(19), Long.fromNumber(20), Long.fromNumber(21), Long.fromNumber(22), Long.fromNumber(23), Long.fromNumber(24), Long.fromNumber(25), Long.fromNumber(26), Long.fromNumber(27), Long.fromNumber(28), Long.fromNumber(29), Long.fromNumber(30), Long.fromNumber(31), Long.fromNumber(32), Long.fromNumber(33), Long.fromNumber(34), Long.fromNumber(35), Long.fromNumber(36), Long.fromNumber(37), Long.fromNumber(38), Long.fromNumber(39), Long.fromNumber(40), Long.fromNumber(41), Long.fromNumber(42), Long.fromNumber(43), Long.fromNumber(44), Long.fromNumber(45), Long.fromNumber(46), Long.fromNumber(47), Long.fromNumber(48), Long.fromNumber(49), Long.fromNumber(50), Long.fromNumber(51), Long.fromNumber(52), Long.fromNumber(53), Long.fromNumber(54), Long.fromNumber(55), Long.fromNumber(56), Long.fromNumber(57), Long.fromNumber(58), Long.fromNumber(59), Long.fromNumber(60), Long.fromNumber(61), Long.fromNumber(62), Long.fromNumber(63)];
                return Base64;
            }());
            utility.Base64 = Base64;
            var BufferView = (function () {
                function BufferView(p_origin, p_initPos, p_endPos) {
                    this._origin = p_origin;
                    this._initPos = p_initPos;
                    this._endPos = p_endPos;
                }
                BufferView.prototype.write = function (b) {
                    throw new Error("Write operation forbidden during iteration");
                };
                BufferView.prototype.writeAll = function (bytes) {
                    throw new Error("Write operation forbidden during iteration");
                };
                BufferView.prototype.read = function (position) {
                    if (this._initPos + position > this._endPos) {
                        throw new Error("" + position);
                    }
                    return this._origin.read(this._initPos + position);
                };
                BufferView.prototype.data = function () {
                    return this._origin.slice(this._initPos, this._endPos);
                };
                BufferView.prototype.length = function () {
                    return this._endPos - this._initPos + 1;
                };
                BufferView.prototype.free = function () {
                    throw new Error("Free operation forbidden during iteration");
                };
                BufferView.prototype.iterator = function () {
                    throw new Error("iterator creation forbidden forbidden during iteration");
                };
                BufferView.prototype.removeLast = function () {
                    throw new Error("Write operation forbidden during iteration");
                };
                BufferView.prototype.slice = function (initPos, endPos) {
                    throw new Error("Write operation forbidden during iteration");
                };
                return BufferView;
            }());
            utility.BufferView = BufferView;
            var DefaultBufferIterator = (function () {
                function DefaultBufferIterator(p_origin) {
                    this._cursor = -1;
                    this._origin = p_origin;
                    this._originSize = p_origin.length();
                }
                DefaultBufferIterator.prototype.hasNext = function () {
                    return this._originSize > 0 && (this._cursor + 1) < this._originSize;
                };
                DefaultBufferIterator.prototype.next = function () {
                    var previousCursor = this._cursor;
                    while ((this._cursor + 1) < this._originSize) {
                        this._cursor++;
                        var current = this._origin.read(this._cursor);
                        if (current == org.mwg.Constants.BUFFER_SEP) {
                            return new org.mwg.utility.BufferView(this._origin, previousCursor + 1, this._cursor - 1);
                        }
                    }
                    if (previousCursor < this._originSize) {
                        return new org.mwg.utility.BufferView(this._origin, previousCursor + 1, this._cursor);
                    }
                    return null;
                };
                return DefaultBufferIterator;
            }());
            utility.DefaultBufferIterator = DefaultBufferIterator;
            var Enforcer = (function () {
                function Enforcer() {
                    this.checkers = new java.util.HashMap();
                }
                Enforcer.prototype.asBool = function (propertyName) {
                    return this.declare(propertyName, {
                        check: function (inputType, input) {
                            {
                                if (input != null && inputType != org.mwg.Type.BOOL) {
                                    throw new Error("Property " + propertyName + " should be Boolean value, currently " + input);
                                }
                            }
                        }
                    });
                };
                Enforcer.prototype.asString = function (propertyName) {
                    return this.declare(propertyName, {
                        check: function (inputType, input) {
                            {
                                if (input != null && inputType != org.mwg.Type.STRING) {
                                    throw new Error("Property " + propertyName + " should be String value, currently " + input);
                                }
                            }
                        }
                    });
                };
                Enforcer.prototype.asLong = function (propertyName) {
                    return this.declare(propertyName, {
                        check: function (inputType, input) {
                            {
                                if (input != null && inputType != org.mwg.Type.LONG && inputType != org.mwg.Type.INT) {
                                    throw new Error("Property " + propertyName + " should be long value, currently " + input);
                                }
                            }
                        }
                    });
                };
                Enforcer.prototype.asLongWithin = function (propertyName, min, max) {
                    return this.declare(propertyName, {
                        check: function (inputType, input) {
                            {
                                var inputDouble = input;
                                if (input != null && ((inputType != org.mwg.Type.LONG && inputType != org.mwg.Type.INT) || inputDouble < min || inputDouble > max)) {
                                    throw new Error("Property " + propertyName + " should be long value [" + min + "," + max + "], currently " + input);
                                }
                            }
                        }
                    });
                };
                Enforcer.prototype.asDouble = function (propertyName) {
                    return this.declare(propertyName, {
                        check: function (inputType, input) {
                            {
                                if (input != null && (inputType != org.mwg.Type.DOUBLE && inputType != org.mwg.Type.INT && inputType != org.mwg.Type.LONG)) {
                                    throw new Error("Property " + propertyName + " should be double value, currently " + input);
                                }
                            }
                        }
                    });
                };
                Enforcer.prototype.asDoubleWithin = function (propertyName, min, max) {
                    return this.declare(propertyName, {
                        check: function (inputType, input) {
                            {
                                var inputDouble = void 0;
                                if (input instanceof Number) {
                                    inputDouble = input;
                                }
                                else if (input instanceof Number) {
                                    inputDouble = input;
                                }
                                else {
                                    inputDouble = input;
                                }
                                if (input != null && ((inputType != org.mwg.Type.DOUBLE && inputType != org.mwg.Type.INT && inputType != org.mwg.Type.LONG) || inputDouble < min || inputDouble > max)) {
                                    throw new Error("Property " + propertyName + " should be double value [" + min + "," + max + "], currently " + input);
                                }
                            }
                        }
                    });
                };
                Enforcer.prototype.asInt = function (propertyName) {
                    return this.declare(propertyName, {
                        check: function (inputType, input) {
                            {
                                if (input != null && inputType != org.mwg.Type.INT && inputType != org.mwg.Type.LONG) {
                                    throw new Error("Property " + propertyName + " should be integer value, currently " + input);
                                }
                            }
                        }
                    });
                };
                Enforcer.prototype.asIntWithin = function (propertyName, min, max) {
                    return this.declare(propertyName, {
                        check: function (inputType, input) {
                            {
                                var inputInt = input;
                                if (input != null && ((inputType != org.mwg.Type.INT && inputType != org.mwg.Type.LONG) || inputInt < min || inputInt > max)) {
                                    throw new Error("Property " + propertyName + " should be integer value [" + min + "," + max + "], currently " + input);
                                }
                            }
                        }
                    });
                };
                Enforcer.prototype.asIntGreaterOrEquals = function (propertyName, min) {
                    return this.declare(propertyName, {
                        check: function (inputType, input) {
                            {
                                var inputInt = input;
                                if (input != null && ((inputType != org.mwg.Type.INT && inputType != org.mwg.Type.LONG) || inputInt < min)) {
                                    throw new Error("Property " + propertyName + " should be integer value >=" + min + ", currently " + input);
                                }
                            }
                        }
                    });
                };
                Enforcer.prototype.asDoubleArray = function (propertyName) {
                    return this.declare(propertyName, {
                        check: function (inputType, input) {
                            {
                                if (input != null && inputType != org.mwg.Type.DOUBLE_ARRAY) {
                                    throw new Error("Property " + propertyName + " should be doubleArray value, currently " + input);
                                }
                            }
                        }
                    });
                };
                Enforcer.prototype.asPositiveInt = function (propertyName) {
                    return this.declare(propertyName, {
                        check: function (inputType, input) {
                            {
                                var inputInt = input;
                                if ((input != null && inputType != org.mwg.Type.INT) || inputInt <= 0) {
                                    throw new Error("Property " + propertyName + " should be a positive integer, currently " + input);
                                }
                            }
                        }
                    });
                };
                Enforcer.prototype.asNonNegativeDouble = function (propertyName) {
                    return this.declare(propertyName, {
                        check: function (inputType, input) {
                            {
                                var inputDouble = void 0;
                                if (input instanceof Number) {
                                    inputDouble = input;
                                }
                                else if (input instanceof Number) {
                                    inputDouble = input;
                                }
                                else {
                                    inputDouble = input;
                                }
                                if (input != null && ((inputType != org.mwg.Type.DOUBLE && inputType != org.mwg.Type.INT && inputType != org.mwg.Type.LONG) || !(inputDouble >= 0))) {
                                    throw new Error("Property " + propertyName + " should be a non-negative double, currently " + input);
                                }
                            }
                        }
                    });
                };
                Enforcer.prototype.asPositiveDouble = function (propertyName) {
                    return this.declare(propertyName, {
                        check: function (inputType, input) {
                            {
                                var inputDouble = void 0;
                                if (input instanceof Number) {
                                    inputDouble = input;
                                }
                                else if (input instanceof Number) {
                                    inputDouble = input;
                                }
                                else {
                                    inputDouble = input;
                                }
                                if (input != null && ((inputType != org.mwg.Type.DOUBLE && inputType != org.mwg.Type.INT && inputType != org.mwg.Type.LONG) || !(inputDouble > 0))) {
                                    throw new Error("Property " + propertyName + " should be a positive double, currently " + input);
                                }
                            }
                        }
                    });
                };
                Enforcer.prototype.asNonNegativeOrNanDouble = function (propertyName) {
                    return this.declare(propertyName, {
                        check: function (inputType, input) {
                            {
                                var inputDouble = void 0;
                                if (input instanceof Number) {
                                    inputDouble = input;
                                }
                                else if (input instanceof Number) {
                                    inputDouble = input;
                                }
                                else {
                                    inputDouble = input;
                                }
                                if (input != null && ((inputType != org.mwg.Type.DOUBLE && inputType != org.mwg.Type.INT && inputType != org.mwg.Type.LONG) || inputDouble < 0)) {
                                    throw new Error("Property " + propertyName + " should be a positive double, currently " + input);
                                }
                            }
                        }
                    });
                };
                Enforcer.prototype.asPositiveLong = function (propertyName) {
                    return this.declare(propertyName, {
                        check: function (inputType, input) {
                            {
                                var inputLong = void 0;
                                if (input instanceof Number) {
                                    inputLong = input;
                                }
                                else {
                                    inputLong = input;
                                }
                                if (input != null && ((inputType != org.mwg.Type.LONG && inputType != org.mwg.Type.INT) || inputLong <= 0)) {
                                    throw new Error("Property " + propertyName + " should be a positive long, currently " + input);
                                }
                            }
                        }
                    });
                };
                Enforcer.prototype.declare = function (propertyName, checker) {
                    this.checkers.put(propertyName, checker);
                    return this;
                };
                Enforcer.prototype.check = function (propertyName, propertyType, propertyValue) {
                    var checker = this.checkers.get(propertyName);
                    if (checker != null) {
                        checker.check(propertyType, propertyValue);
                    }
                };
                return Enforcer;
            }());
            utility.Enforcer = Enforcer;
            var HashHelper = (function () {
                function HashHelper() {
                }
                HashHelper.longHash = function (number, max) {
                    if (max <= 0) {
                        throw new Error("Max must be > 0");
                    }
                    var crc = HashHelper.PRIME5;
                    crc += number;
                    crc += crc << 17;
                    crc *= HashHelper.PRIME4;
                    crc *= HashHelper.PRIME1;
                    crc += number;
                    crc += crc << 17;
                    crc *= HashHelper.PRIME4;
                    crc *= HashHelper.PRIME1;
                    crc += HashHelper.len;
                    crc ^= crc >>> 15;
                    crc *= HashHelper.PRIME2;
                    crc += number;
                    crc ^= crc >>> 13;
                    crc *= HashHelper.PRIME3;
                    crc ^= crc >>> 16;
                    crc = (crc < 0 ? crc * -1 : crc);
                    crc = crc % max;
                    return crc;
                };
                HashHelper.tripleHash = function (p0, p1, p2, p3, max) {
                    if (max <= 0) {
                        throw new Error("Max must be > 0");
                    }
                    var v1 = HashHelper.PRIME5;
                    var v2 = v1 * HashHelper.PRIME2 + HashHelper.len;
                    var v3 = v2 * HashHelper.PRIME3;
                    var v4 = v3 * HashHelper.PRIME4;
                    var crc;
                    v1 = ((v1 << 13) | (v1 >>> 51)) + p1;
                    v2 = ((v2 << 11) | (v2 >>> 53)) + p2;
                    v3 = ((v3 << 17) | (v3 >>> 47)) + p3;
                    v4 = ((v4 << 19) | (v4 >>> 45)) + p0;
                    v1 += ((v1 << 17) | (v1 >>> 47));
                    v2 += ((v2 << 19) | (v2 >>> 45));
                    v3 += ((v3 << 13) | (v3 >>> 51));
                    v4 += ((v4 << 11) | (v4 >>> 53));
                    v1 *= HashHelper.PRIME1;
                    v2 *= HashHelper.PRIME1;
                    v3 *= HashHelper.PRIME1;
                    v4 *= HashHelper.PRIME1;
                    v1 += p1;
                    v2 += p2;
                    v3 += p3;
                    v4 += HashHelper.PRIME5;
                    v1 *= HashHelper.PRIME2;
                    v2 *= HashHelper.PRIME2;
                    v3 *= HashHelper.PRIME2;
                    v4 *= HashHelper.PRIME2;
                    v1 += ((v1 << 11) | (v1 >>> 53));
                    v2 += ((v2 << 17) | (v2 >>> 47));
                    v3 += ((v3 << 19) | (v3 >>> 45));
                    v4 += ((v4 << 13) | (v4 >>> 51));
                    v1 *= HashHelper.PRIME3;
                    v2 *= HashHelper.PRIME3;
                    v3 *= HashHelper.PRIME3;
                    v4 *= HashHelper.PRIME3;
                    crc = v1 + ((v2 << 3) | (v2 >>> 61)) + ((v3 << 6) | (v3 >>> 58)) + ((v4 << 9) | (v4 >>> 55));
                    crc ^= crc >>> 11;
                    crc += (HashHelper.PRIME4 + HashHelper.len) * HashHelper.PRIME1;
                    crc ^= crc >>> 15;
                    crc *= HashHelper.PRIME2;
                    crc ^= crc >>> 13;
                    crc = (crc < 0 ? crc * -1 : crc);
                    crc = crc % max;
                    return crc;
                };
                HashHelper.rand = function () {
                    return Math.random() * 1000000;
                };
                HashHelper.equals = function (src, other) {
                    return src === other;
                };
                HashHelper.DOUBLE_MIN_VALUE = function () {
                    return Number.MIN_VALUE;
                };
                HashHelper.DOUBLE_MAX_VALUE = function () {
                    return Number.MAX_VALUE;
                };
                HashHelper.isDefined = function (param) {
                    return param != undefined && param != null;
                };
                HashHelper.hash = function (data) {
                    var hash = 0, i, chr, len;
                    if (data.length === 0)
                        return hash;
                    for (i = 0, len = data.length; i < len; i++) {
                        chr = data.charCodeAt(i);
                        hash = ((hash << 5) - hash) + chr;
                        hash |= 0; // Convert to 32bit integer
                    }
                    return hash;
                };
                HashHelper.hashBytes = function (data) {
                    var h = org.mwg.utility.HashHelper.HSTART;
                    var dataLength = data.length;
                    for (var i = 0; i < dataLength; i++) {
                        h = h.mul(org.mwg.utility.HashHelper.HMULT).xor(org.mwg.utility.HashHelper.byteTable[data[i] & 0xff]);
                    }
                    return h.mod(org.mwg.core.CoreConstants.END_OF_TIME).toNumber();
                };
                HashHelper.PRIME1 = 2654435761;
                HashHelper.PRIME2 = 2246822519;
                HashHelper.PRIME3 = 3266489917;
                HashHelper.PRIME4 = 668265263;
                HashHelper.PRIME5 = 0x165667b1;
                HashHelper.len = 24;
                HashHelper.byteTable = function () {
                    var table = [];
                    var h = Long.fromBits(0xCAAF1684, 0x544B2FBA);
                    for (var i = 0; i < 256; i++) {
                        for (var j = 0; j < 31; j++) {
                            h = h.shiftRightUnsigned(7).xor(h);
                            h = h.shiftLeft(11).xor(h);
                            h = h.shiftRightUnsigned(10).xor(h);
                        }
                        table[i] = h.toSigned();
                    }
                    return table;
                }();
                HashHelper.HSTART = Long.fromBits(0xA205B064, 0xBB40E64D);
                HashHelper.HMULT = Long.fromBits(0xE116586D, 0x6A5D39EA);
                return HashHelper;
            }());
            utility.HashHelper = HashHelper;
            var KeyHelper = (function () {
                function KeyHelper() {
                }
                KeyHelper.keyToBuffer = function (buffer, chunkType, world, time, id) {
                    org.mwg.utility.Base64.encodeIntToBuffer(chunkType, buffer);
                    buffer.write(org.mwg.Constants.KEY_SEP);
                    org.mwg.utility.Base64.encodeLongToBuffer(world, buffer);
                    buffer.write(org.mwg.Constants.KEY_SEP);
                    org.mwg.utility.Base64.encodeLongToBuffer(time, buffer);
                    buffer.write(org.mwg.Constants.KEY_SEP);
                    org.mwg.utility.Base64.encodeLongToBuffer(id, buffer);
                };
                return KeyHelper;
            }());
            utility.KeyHelper = KeyHelper;
            var Tuple = (function () {
                function Tuple(p_left, p_right) {
                    this._left = p_left;
                    this._right = p_right;
                }
                Tuple.prototype.left = function () {
                    return this._left;
                };
                Tuple.prototype.right = function () {
                    return this._right;
                };
                return Tuple;
            }());
            utility.Tuple = Tuple;
            var VerboseHook = (function () {
                function VerboseHook() {
                    this.ctxIdents = new java.util.HashMap();
                }
                VerboseHook.prototype.start = function (initialContext) {
                    this.ctxIdents.put(initialContext, 0);
                    console.log("StartTask:" + initialContext);
                };
                VerboseHook.prototype.beforeAction = function (action, context) {
                    var currentPrefix = this.ctxIdents.get(context);
                    for (var i = 0; i < currentPrefix; i++) {
                        console.log("\t");
                    }
                    var taskName = action.toString();
                    console.log(context.template(taskName));
                };
                VerboseHook.prototype.afterAction = function (action, context) { };
                VerboseHook.prototype.beforeTask = function (parentContext, context) {
                    var currentPrefix = this.ctxIdents.get(parentContext);
                    this.ctxIdents.put(context, currentPrefix + 1);
                };
                VerboseHook.prototype.afterTask = function (context) {
                    this.ctxIdents.remove(context);
                };
                VerboseHook.prototype.end = function (finalContext) {
                    console.log("EndTask:" + finalContext.toString());
                };
                return VerboseHook;
            }());
            utility.VerboseHook = VerboseHook;
            var VerboseHookFactory = (function () {
                function VerboseHookFactory() {
                }
                VerboseHookFactory.prototype.newHook = function () {
                    return new org.mwg.utility.VerboseHook();
                };
                return VerboseHookFactory;
            }());
            utility.VerboseHookFactory = VerboseHookFactory;
            var VerbosePlugin = (function (_super) {
                __extends(VerbosePlugin, _super);
                function VerbosePlugin() {
                    _super.call(this);
                    this.declareTaskHookFactory(new org.mwg.utility.VerboseHookFactory());
                }
                return VerbosePlugin;
            }(org.mwg.plugin.AbstractPlugin));
            utility.VerbosePlugin = VerbosePlugin;
        })(utility = mwg.utility || (mwg.utility = {}));
    })(mwg = org.mwg || (org.mwg = {}));
})(org || (org = {}));
/// <reference path="api.ts" />
var org;
(function (org) {
    var mwg;
    (function (mwg) {
        var core;
        (function (core) {
            var BlackHoleStorage = (function () {
                function BlackHoleStorage() {
                    this.prefix = 0;
                }
                BlackHoleStorage.prototype.get = function (keys, callback) {
                    var result = this._graph.newBuffer();
                    var it = keys.iterator();
                    var isFirst = true;
                    while (it.hasNext()) {
                        var tempView = it.next();
                        if (isFirst) {
                            isFirst = false;
                        }
                        else {
                            result.write(org.mwg.core.CoreConstants.BUFFER_SEP);
                        }
                    }
                    callback(result);
                };
                BlackHoleStorage.prototype.put = function (stream, callback) {
                    if (callback != null) {
                        callback(true);
                    }
                };
                BlackHoleStorage.prototype.remove = function (keys, callback) {
                    callback(true);
                };
                BlackHoleStorage.prototype.connect = function (graph, callback) {
                    this._graph = graph;
                    callback(true);
                };
                BlackHoleStorage.prototype.lock = function (callback) {
                    var buffer = this._graph.newBuffer();
                    org.mwg.utility.Base64.encodeIntToBuffer(this.prefix, buffer);
                    this.prefix++;
                    callback(buffer);
                };
                BlackHoleStorage.prototype.unlock = function (previousLock, callback) {
                    callback(true);
                };
                BlackHoleStorage.prototype.disconnect = function (callback) {
                    this._graph = null;
                    callback(true);
                };
                return BlackHoleStorage;
            }());
            core.BlackHoleStorage = BlackHoleStorage;
            var Builder = (function () {
                function Builder() {
                }
                Builder.prototype.newGraph = function (p_storage, p_readOnly, p_scheduler, p_plugins, p_memorySize) {
                    var storage = p_storage;
                    if (storage == null) {
                        storage = new org.mwg.core.BlackHoleStorage();
                    }
                    if (p_readOnly) {
                        storage = new org.mwg.core.utility.ReadOnlyStorage(storage);
                    }
                    var scheduler = p_scheduler;
                    if (scheduler == null) {
                        scheduler = new org.mwg.core.scheduler.TrampolineScheduler();
                    }
                    var memorySize = p_memorySize;
                    if (memorySize == -1) {
                        memorySize = 100000;
                    }
                    return new org.mwg.core.CoreGraph(storage, memorySize, scheduler, p_plugins);
                };
                Builder.prototype.newTask = function () {
                    return new org.mwg.core.task.CoreTask();
                };
                return Builder;
            }());
            core.Builder = Builder;
            var CoreConstants = (function (_super) {
                __extends(CoreConstants, _super);
                function CoreConstants() {
                    _super.apply(this, arguments);
                }
                CoreConstants.PREFIX_TO_SAVE_SIZE = 2;
                CoreConstants.NULL_KEY = new Float64Array([org.mwg.Constants.END_OF_TIME, org.mwg.Constants.END_OF_TIME, org.mwg.Constants.END_OF_TIME]);
                CoreConstants.GLOBAL_UNIVERSE_KEY = new Float64Array([org.mwg.Constants.NULL_LONG, org.mwg.Constants.NULL_LONG, org.mwg.Constants.NULL_LONG]);
                CoreConstants.GLOBAL_DICTIONARY_KEY = new Float64Array([org.mwg.Constants.NULL_LONG, 0, 0]);
                CoreConstants.GLOBAL_INDEX_KEY = new Float64Array([org.mwg.Constants.NULL_LONG, 1, 0]);
                CoreConstants.INDEX_ATTRIBUTE = "index";
                CoreConstants.DISCONNECTED_ERROR = "Please connect your graph, prior to any usage of it";
                CoreConstants.SCALE_1 = 1000;
                CoreConstants.SCALE_2 = 10000;
                CoreConstants.SCALE_3 = 100000;
                CoreConstants.SCALE_4 = 1000000;
                CoreConstants.DEAD_NODE_ERROR = "This Node has been tagged destroyed, please don't use it anymore!";
                return CoreConstants;
            }(org.mwg.Constants));
            core.CoreConstants = CoreConstants;
            var CoreGraph = (function () {
                function CoreGraph(p_storage, memorySize, p_scheduler, p_plugins) {
                    this._prefix = null;
                    this._nodeKeyCalculator = null;
                    this._worldKeyCalculator = null;
                    var selfPointer = this;
                    var memoryFactory = null;
                    var resolverFactory = null;
                    var hookFactory = null;
                    if (p_plugins != null) {
                        for (var i = 0; i < p_plugins.length; i++) {
                            var loopPlugin = p_plugins[i];
                            var loopMF = loopPlugin.memoryFactory();
                            var loopHF = loopPlugin.hookFactory();
                            if (loopMF != null) {
                                memoryFactory = loopMF;
                            }
                            var loopRF = loopPlugin.resolverFactory();
                            if (loopRF != null) {
                                resolverFactory = loopRF;
                            }
                            if (loopHF != null) {
                                hookFactory = loopHF;
                            }
                        }
                    }
                    if (memoryFactory == null) {
                        memoryFactory = new org.mwg.core.memory.HeapMemoryFactory();
                    }
                    if (resolverFactory == null) {
                        resolverFactory = {
                            newResolver: function (storage, space) {
                                {
                                    return new org.mwg.core.MWGResolver(storage, space, selfPointer);
                                }
                            }
                        };
                    }
                    this._hookFactory = hookFactory;
                    this._storage = p_storage;
                    this._memoryFactory = memoryFactory;
                    this._space = memoryFactory.newSpace(memorySize, selfPointer);
                    this._resolver = resolverFactory.newResolver(this._storage, this._space);
                    this._scheduler = p_scheduler;
                    this._taskActions = new java.util.HashMap();
                    org.mwg.core.task.CoreTask.fillDefault(this._taskActions);
                    if (p_plugins != null) {
                        this._nodeTypes = new java.util.HashMap();
                        for (var i = 0; i < p_plugins.length; i++) {
                            var loopPlugin = p_plugins[i];
                            var plugin_names = loopPlugin.nodeTypes();
                            for (var j = 0; j < plugin_names.length; j++) {
                                var plugin_name = plugin_names[j];
                                this._nodeTypes.put(this._resolver.stringToHash(plugin_name, false), loopPlugin.nodeType(plugin_name));
                            }
                            var task_names = loopPlugin.taskActionTypes();
                            for (var j = 0; j < task_names.length; j++) {
                                var task_name = task_names[j];
                                this._taskActions.put(task_name, loopPlugin.taskActionType(task_name));
                            }
                        }
                    }
                    else {
                        this._nodeTypes = null;
                    }
                    this._isConnected = new java.util.concurrent.atomic.AtomicBoolean(false);
                    this._lock = new java.util.concurrent.atomic.AtomicBoolean(false);
                    this._plugins = p_plugins;
                }
                CoreGraph.prototype.fork = function (world) {
                    var childWorld = this._worldKeyCalculator.newKey();
                    this._resolver.initWorld(world, childWorld);
                    return childWorld;
                };
                CoreGraph.prototype.newNode = function (world, time) {
                    if (!this._isConnected.get()) {
                        throw new Error(org.mwg.core.CoreConstants.DISCONNECTED_ERROR);
                    }
                    var newNode = new org.mwg.core.CoreNode(world, time, this._nodeKeyCalculator.newKey(), this);
                    this._resolver.initNode(newNode, org.mwg.Constants.NULL_LONG);
                    return newNode;
                };
                CoreGraph.prototype.newTypedNode = function (world, time, nodeType) {
                    if (nodeType == null) {
                        throw new Error("nodeType should not be null");
                    }
                    if (!this._isConnected.get()) {
                        throw new Error(org.mwg.core.CoreConstants.DISCONNECTED_ERROR);
                    }
                    var extraCode = this._resolver.stringToHash(nodeType, false);
                    var resolvedFactory = this.factoryByCode(extraCode);
                    var newNode;
                    if (resolvedFactory == null) {
                        console.log("WARNING: UnKnow NodeType " + nodeType + ", missing plugin configuration in the builder ? Using generic node as a fallback");
                        newNode = new org.mwg.core.CoreNode(world, time, this._nodeKeyCalculator.newKey(), this);
                    }
                    else {
                        newNode = resolvedFactory(world, time, this._nodeKeyCalculator.newKey(), this);
                    }
                    this._resolver.initNode(newNode, extraCode);
                    return newNode;
                };
                CoreGraph.prototype.cloneNode = function (origin) {
                    if (origin == null) {
                        throw new Error("origin node should not be null");
                    }
                    if (!this._isConnected.get()) {
                        throw new Error(org.mwg.core.CoreConstants.DISCONNECTED_ERROR);
                    }
                    var casted = origin;
                    casted.cacheLock();
                    if (casted._dead) {
                        casted.cacheUnlock();
                        throw new Error(org.mwg.core.CoreConstants.DEAD_NODE_ERROR + " node id: " + casted.id());
                    }
                    else {
                        this._space.mark(casted._index_stateChunk);
                        this._space.mark(casted._index_superTimeTree);
                        this._space.mark(casted._index_timeTree);
                        this._space.mark(casted._index_worldOrder);
                        var worldOrderChunk = this._space.get(casted._index_worldOrder);
                        var resolvedFactory = this.factoryByCode(worldOrderChunk.extra());
                        var newNode = void 0;
                        if (resolvedFactory == null) {
                            newNode = new org.mwg.core.CoreNode(origin.world(), origin.time(), origin.id(), this);
                        }
                        else {
                            newNode = resolvedFactory(origin.world(), origin.time(), origin.id(), this);
                        }
                        newNode._index_stateChunk = casted._index_stateChunk;
                        newNode._index_timeTree = casted._index_timeTree;
                        newNode._index_superTimeTree = casted._index_superTimeTree;
                        newNode._index_worldOrder = casted._index_worldOrder;
                        newNode._world_magic = casted._world_magic;
                        newNode._super_time_magic = casted._super_time_magic;
                        newNode._time_magic = casted._time_magic;
                        casted.cacheUnlock();
                        return newNode;
                    }
                };
                CoreGraph.prototype.factoryByCode = function (code) {
                    if (this._nodeTypes != null && code != org.mwg.Constants.NULL_LONG) {
                        return this._nodeTypes.get(code);
                    }
                    else {
                        return null;
                    }
                };
                CoreGraph.prototype.taskAction = function (taskActionName) {
                    if (this._taskActions != null && taskActionName != null) {
                        return this._taskActions.get(taskActionName);
                    }
                    else {
                        return null;
                    }
                };
                CoreGraph.prototype.taskHookFactory = function () {
                    return this._hookFactory;
                };
                CoreGraph.prototype.lookup = function (world, time, id, callback) {
                    if (!this._isConnected.get()) {
                        throw new Error(org.mwg.core.CoreConstants.DISCONNECTED_ERROR);
                    }
                    this._resolver.lookup(world, time, id, callback);
                };
                CoreGraph.prototype.lookupAll = function (world, time, ids, callback) {
                    if (!this._isConnected.get()) {
                        throw new Error(org.mwg.core.CoreConstants.DISCONNECTED_ERROR);
                    }
                    this._resolver.lookupAll(world, time, ids, callback);
                };
                CoreGraph.prototype.save = function (callback) {
                    this._space.save(callback);
                };
                CoreGraph.prototype.connect = function (callback) {
                    var _this = this;
                    var selfPointer = this;
                    while (selfPointer._lock.compareAndSet(false, true))
                        if (this._isConnected.compareAndSet(false, true)) {
                            selfPointer._scheduler.start();
                            selfPointer._storage.connect(selfPointer, function (connection) {
                                {
                                    selfPointer._storage.lock(function (prefixBuf) {
                                        {
                                            _this._prefix = org.mwg.utility.Base64.decodeToIntWithBounds(prefixBuf, 0, prefixBuf.length());
                                            prefixBuf.free();
                                            var connectionKeys_1 = selfPointer.newBuffer();
                                            org.mwg.utility.KeyHelper.keyToBuffer(connectionKeys_1, org.mwg.chunk.ChunkType.GEN_CHUNK, org.mwg.Constants.BEGINNING_OF_TIME, org.mwg.Constants.NULL_LONG, _this._prefix);
                                            connectionKeys_1.write(org.mwg.core.CoreConstants.BUFFER_SEP);
                                            org.mwg.utility.KeyHelper.keyToBuffer(connectionKeys_1, org.mwg.chunk.ChunkType.GEN_CHUNK, org.mwg.Constants.END_OF_TIME, org.mwg.Constants.NULL_LONG, _this._prefix);
                                            connectionKeys_1.write(org.mwg.core.CoreConstants.BUFFER_SEP);
                                            org.mwg.utility.KeyHelper.keyToBuffer(connectionKeys_1, org.mwg.chunk.ChunkType.WORLD_ORDER_CHUNK, 0, 0, org.mwg.Constants.NULL_LONG);
                                            connectionKeys_1.write(org.mwg.core.CoreConstants.BUFFER_SEP);
                                            org.mwg.utility.KeyHelper.keyToBuffer(connectionKeys_1, org.mwg.chunk.ChunkType.STATE_CHUNK, org.mwg.core.CoreConstants.GLOBAL_DICTIONARY_KEY[0], org.mwg.core.CoreConstants.GLOBAL_DICTIONARY_KEY[1], org.mwg.core.CoreConstants.GLOBAL_DICTIONARY_KEY[2]);
                                            connectionKeys_1.write(org.mwg.core.CoreConstants.BUFFER_SEP);
                                            selfPointer._storage.get(connectionKeys_1, function (payloads) {
                                                {
                                                    connectionKeys_1.free();
                                                    if (payloads != null) {
                                                        var it = payloads.iterator();
                                                        var view1 = it.next();
                                                        var view2 = it.next();
                                                        var view3 = it.next();
                                                        var view4 = it.next();
                                                        var noError = true;
                                                        try {
                                                            var globalWorldOrder = selfPointer._space.createAndMark(org.mwg.chunk.ChunkType.WORLD_ORDER_CHUNK, 0, 0, org.mwg.Constants.NULL_LONG);
                                                            if (view3.length() > 0) {
                                                                globalWorldOrder.load(view3);
                                                            }
                                                            var globalDictionaryChunk = selfPointer._space.createAndMark(org.mwg.chunk.ChunkType.STATE_CHUNK, org.mwg.core.CoreConstants.GLOBAL_DICTIONARY_KEY[0], org.mwg.core.CoreConstants.GLOBAL_DICTIONARY_KEY[1], org.mwg.core.CoreConstants.GLOBAL_DICTIONARY_KEY[2]);
                                                            if (view4.length() > 0) {
                                                                globalDictionaryChunk.load(view4);
                                                            }
                                                            selfPointer._worldKeyCalculator = selfPointer._space.createAndMark(org.mwg.chunk.ChunkType.GEN_CHUNK, org.mwg.Constants.END_OF_TIME, org.mwg.Constants.NULL_LONG, _this._prefix);
                                                            if (view2.length() > 0) {
                                                                selfPointer._worldKeyCalculator.load(view2);
                                                            }
                                                            selfPointer._nodeKeyCalculator = selfPointer._space.createAndMark(org.mwg.chunk.ChunkType.GEN_CHUNK, org.mwg.Constants.BEGINNING_OF_TIME, org.mwg.Constants.NULL_LONG, _this._prefix);
                                                            if (view1.length() > 0) {
                                                                selfPointer._nodeKeyCalculator.load(view1);
                                                            }
                                                            selfPointer._resolver.init();
                                                            if (_this._plugins != null) {
                                                                for (var i = 0; i < _this._plugins.length; i++) {
                                                                    var nodeTypes = _this._plugins[i].nodeTypes();
                                                                    if (nodeTypes != null) {
                                                                        for (var j = 0; j < nodeTypes.length; j++) {
                                                                            var pluginName = nodeTypes[j];
                                                                            selfPointer._resolver.stringToHash(pluginName, true);
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        catch ($ex$) {
                                                            if ($ex$ instanceof Error) {
                                                                var e = $ex$;
                                                                {
                                                                    console.error(e);
                                                                    noError = false;
                                                                }
                                                            }
                                                            else {
                                                                throw $ex$;
                                                            }
                                                        }
                                                        payloads.free();
                                                        selfPointer._lock.set(true);
                                                        if (org.mwg.utility.HashHelper.isDefined(callback)) {
                                                            callback(noError);
                                                        }
                                                    }
                                                    else {
                                                        selfPointer._lock.set(true);
                                                        if (org.mwg.utility.HashHelper.isDefined(callback)) {
                                                            callback(false);
                                                        }
                                                    }
                                                }
                                            });
                                        }
                                    });
                                }
                            });
                        }
                        else {
                            selfPointer._lock.set(true);
                            if (org.mwg.utility.HashHelper.isDefined(callback)) {
                                callback(null);
                            }
                        }
                };
                CoreGraph.prototype.disconnect = function (callback) {
                    var _loop_1 = function() {
                        if (this_1._isConnected.compareAndSet(true, false)) {
                            var selfPointer_2 = this_1;
                            selfPointer_2._scheduler.stop();
                            this_1.save(function (result) {
                                {
                                    selfPointer_2._space.freeAll();
                                    if (selfPointer_2._storage != null) {
                                        var prefixBuf_1 = selfPointer_2.newBuffer();
                                        org.mwg.utility.Base64.encodeIntToBuffer(selfPointer_2._prefix, prefixBuf_1);
                                        selfPointer_2._storage.unlock(prefixBuf_1, function (result) {
                                            {
                                                prefixBuf_1.free();
                                                selfPointer_2._storage.disconnect(function (result) {
                                                    {
                                                        selfPointer_2._lock.set(true);
                                                        if (org.mwg.utility.HashHelper.isDefined(callback)) {
                                                            callback(result);
                                                        }
                                                    }
                                                });
                                            }
                                        });
                                    }
                                    else {
                                        selfPointer_2._lock.set(true);
                                        if (org.mwg.utility.HashHelper.isDefined(callback)) {
                                            callback(result);
                                        }
                                    }
                                }
                            });
                        }
                        else {
                            this_1._lock.set(true);
                            if (org.mwg.utility.HashHelper.isDefined(callback)) {
                                callback(null);
                            }
                        }
                    };
                    var this_1 = this;
                    while (this._lock.compareAndSet(false, true)) {
                        _loop_1();
                    }
                };
                CoreGraph.prototype.newBuffer = function () {
                    return this._memoryFactory.newBuffer();
                };
                CoreGraph.prototype.newQuery = function () {
                    return new org.mwg.core.CoreQuery(this, this._resolver);
                };
                CoreGraph.prototype.index = function (indexName, toIndexNode, flatKeyAttributes, callback) {
                    this.indexAt(toIndexNode.world(), toIndexNode.time(), indexName, toIndexNode, flatKeyAttributes, callback);
                };
                CoreGraph.prototype.indexAt = function (world, time, indexName, nodeToIndex, flatKeyAttributes, callback) {
                    if (indexName == null) {
                        throw new Error("indexName should not be null");
                    }
                    if (nodeToIndex == null) {
                        throw new Error("toIndexNode should not be null");
                    }
                    if (flatKeyAttributes == null) {
                        throw new Error("flatKeyAttributes should not be null");
                    }
                    this.getIndexOrCreate(world, time, indexName, true, function (foundIndex) {
                        {
                            if (foundIndex == null) {
                                throw new Error("Index creation failed, cache is probably full !!!");
                            }
                            foundIndex.index(org.mwg.core.CoreConstants.INDEX_ATTRIBUTE, nodeToIndex, flatKeyAttributes, function (result) {
                                {
                                    foundIndex.free();
                                    if (org.mwg.utility.HashHelper.isDefined(callback)) {
                                        callback(result);
                                    }
                                }
                            });
                        }
                    });
                };
                CoreGraph.prototype.unindex = function (indexName, nodeToUnindex, flatKeyAttributes, callback) {
                    this.unindexAt(nodeToUnindex.world(), nodeToUnindex.time(), indexName, nodeToUnindex, flatKeyAttributes, callback);
                };
                CoreGraph.prototype.unindexAt = function (world, time, indexName, nodeToUnindex, flatKeyAttributes, callback) {
                    if (indexName == null) {
                        throw new Error("indexName should not be null");
                    }
                    if (nodeToUnindex == null) {
                        throw new Error("toIndexNode should not be null");
                    }
                    if (flatKeyAttributes == null) {
                        throw new Error("flatKeyAttributes should not be null");
                    }
                    this.getIndexOrCreate(world, time, indexName, false, function (foundIndex) {
                        {
                            if (foundIndex != null) {
                                foundIndex.unindex(org.mwg.core.CoreConstants.INDEX_ATTRIBUTE, nodeToUnindex, flatKeyAttributes, function (result) {
                                    {
                                        foundIndex.free();
                                        if (org.mwg.utility.HashHelper.isDefined(callback)) {
                                            callback(result);
                                        }
                                    }
                                });
                            }
                            else {
                                if (org.mwg.utility.HashHelper.isDefined(callback)) {
                                    callback(false);
                                }
                            }
                        }
                    });
                };
                CoreGraph.prototype.indexes = function (world, time, callback) {
                    var selfPointer = this;
                    this._resolver.lookup(world, time, org.mwg.core.CoreConstants.END_OF_TIME, function (globalIndexNodeUnsafe) {
                        {
                            if (globalIndexNodeUnsafe == null) {
                                callback(new Array(0));
                            }
                            else {
                                var globalIndexContent = globalIndexNodeUnsafe.get(org.mwg.core.CoreConstants.INDEX_ATTRIBUTE);
                                if (globalIndexContent == null) {
                                    globalIndexNodeUnsafe.free();
                                    callback(new Array(0));
                                }
                                else {
                                    var result_1 = new Array(globalIndexContent.size());
                                    var resultIndex_1 = new Int32Array([0]);
                                    globalIndexContent.each(function (key, value) {
                                        {
                                            result_1[resultIndex_1[0]] = selfPointer._resolver.hashToString(key);
                                            resultIndex_1[0]++;
                                        }
                                    });
                                    globalIndexNodeUnsafe.free();
                                    callback(result_1);
                                }
                            }
                        }
                    });
                };
                CoreGraph.prototype.find = function (world, time, indexName, query, callback) {
                    if (indexName == null) {
                        throw new Error("indexName should not be null");
                    }
                    if (query == null) {
                        throw new Error("query should not be null");
                    }
                    this.getIndexOrCreate(world, time, indexName, false, function (foundIndex) {
                        {
                            if (foundIndex == null) {
                                if (org.mwg.utility.HashHelper.isDefined(callback)) {
                                    callback(new Array(0));
                                }
                            }
                            else {
                                foundIndex.find(org.mwg.core.CoreConstants.INDEX_ATTRIBUTE, query, function (collectedNodes) {
                                    {
                                        foundIndex.free();
                                        if (org.mwg.utility.HashHelper.isDefined(callback)) {
                                            callback(collectedNodes);
                                        }
                                    }
                                });
                            }
                        }
                    });
                };
                CoreGraph.prototype.findByQuery = function (query, callback) {
                    if (query == null) {
                        throw new Error("query should not be null");
                    }
                    if (query.world() == org.mwg.Constants.NULL_LONG) {
                        throw new Error("Please fill world parameter in query before first usage!");
                    }
                    if (query.time() == org.mwg.Constants.NULL_LONG) {
                        throw new Error("Please fill time parameter in query before first usage!");
                    }
                    if (query.indexName() == null) {
                        throw new Error("Please fill indexName parameter in query before first usage!");
                    }
                    this.getIndexOrCreate(query.world(), query.time(), query.indexName(), false, function (foundIndex) {
                        {
                            if (foundIndex == null) {
                                if (org.mwg.utility.HashHelper.isDefined(callback)) {
                                    callback(new Array(0));
                                }
                            }
                            else {
                                query.setIndexName(org.mwg.core.CoreConstants.INDEX_ATTRIBUTE);
                                foundIndex.findByQuery(query, function (collectedNodes) {
                                    {
                                        foundIndex.free();
                                        if (org.mwg.utility.HashHelper.isDefined(callback)) {
                                            callback(collectedNodes);
                                        }
                                    }
                                });
                            }
                        }
                    });
                };
                CoreGraph.prototype.findAll = function (world, time, indexName, callback) {
                    if (indexName == null) {
                        throw new Error("indexName should not be null");
                    }
                    this.getIndexOrCreate(world, time, indexName, false, function (foundIndex) {
                        {
                            if (foundIndex == null) {
                                if (org.mwg.utility.HashHelper.isDefined(callback)) {
                                    callback(new Array(0));
                                }
                            }
                            else {
                                foundIndex.findAll(org.mwg.core.CoreConstants.INDEX_ATTRIBUTE, function (collectedNodes) {
                                    {
                                        foundIndex.free();
                                        if (org.mwg.utility.HashHelper.isDefined(callback)) {
                                            callback(collectedNodes);
                                        }
                                    }
                                });
                            }
                        }
                    });
                };
                CoreGraph.prototype.getIndexNode = function (world, time, indexName, callback) {
                    if (indexName == null) {
                        throw new Error("indexName should not be null");
                    }
                    this.getIndexOrCreate(world, time, indexName, false, callback);
                };
                CoreGraph.prototype.getIndexOrCreate = function (world, time, indexName, createIfNull, callback) {
                    var selfPointer = this;
                    var indexNameCoded = this._resolver.stringToHash(indexName, createIfNull);
                    this._resolver.lookup(world, time, org.mwg.core.CoreConstants.END_OF_TIME, function (globalIndexNodeUnsafe) {
                        {
                            if (globalIndexNodeUnsafe == null && !createIfNull) {
                                callback(null);
                            }
                            else {
                                var globalIndexContent = void 0;
                                if (globalIndexNodeUnsafe == null) {
                                    globalIndexNodeUnsafe = new org.mwg.core.CoreNode(world, time, org.mwg.core.CoreConstants.END_OF_TIME, selfPointer);
                                    selfPointer._resolver.initNode(globalIndexNodeUnsafe, org.mwg.core.CoreConstants.NULL_LONG);
                                    globalIndexContent = globalIndexNodeUnsafe.getOrCreate(org.mwg.core.CoreConstants.INDEX_ATTRIBUTE, org.mwg.Type.LONG_TO_LONG_MAP);
                                }
                                else {
                                    globalIndexContent = globalIndexNodeUnsafe.get(org.mwg.core.CoreConstants.INDEX_ATTRIBUTE);
                                }
                                var indexId = globalIndexContent.get(indexNameCoded);
                                globalIndexNodeUnsafe.free();
                                if (indexId == org.mwg.core.CoreConstants.NULL_LONG) {
                                    if (createIfNull) {
                                        var newIndexNode = selfPointer.newNode(world, time);
                                        newIndexNode.getOrCreate(org.mwg.core.CoreConstants.INDEX_ATTRIBUTE, org.mwg.Type.LONG_TO_LONG_ARRAY_MAP);
                                        indexId = newIndexNode.id();
                                        globalIndexContent.put(indexNameCoded, indexId);
                                        callback(newIndexNode);
                                    }
                                    else {
                                        callback(null);
                                    }
                                }
                                else {
                                    selfPointer._resolver.lookup(world, time, indexId, callback);
                                }
                            }
                        }
                    });
                };
                CoreGraph.prototype.newCounter = function (expectedCountCalls) {
                    return new org.mwg.core.utility.CoreDeferCounter(expectedCountCalls);
                };
                CoreGraph.prototype.newSyncCounter = function (expectedCountCalls) {
                    return new org.mwg.core.utility.CoreDeferCounterSync(expectedCountCalls);
                };
                CoreGraph.prototype.resolver = function () {
                    return this._resolver;
                };
                CoreGraph.prototype.scheduler = function () {
                    return this._scheduler;
                };
                CoreGraph.prototype.space = function () {
                    return this._space;
                };
                CoreGraph.prototype.storage = function () {
                    return this._storage;
                };
                CoreGraph.prototype.freeNodes = function (nodes) {
                    if (nodes != null) {
                        for (var i = 0; i < nodes.length; i++) {
                            if (nodes[i] != null) {
                                nodes[i].free();
                            }
                        }
                    }
                };
                return CoreGraph;
            }());
            core.CoreGraph = CoreGraph;
            var CoreNode = (function (_super) {
                __extends(CoreNode, _super);
                function CoreNode(p_world, p_time, p_id, p_graph) {
                    _super.call(this, p_world, p_time, p_id, p_graph);
                }
                return CoreNode;
            }(org.mwg.plugin.AbstractNode));
            core.CoreNode = CoreNode;
            var CoreQuery = (function () {
                function CoreQuery(graph, p_resolver) {
                    this.capacity = 1;
                    this._attributes = new Float64Array(this.capacity);
                    this._values = new Array(this.capacity);
                    this.size = 0;
                    this._world = org.mwg.Constants.NULL_LONG;
                    this._time = org.mwg.Constants.NULL_LONG;
                    this._indexName = null;
                    this._graph = graph;
                    this._resolver = p_resolver;
                    this._hash = null;
                }
                CoreQuery.prototype.parse = function (flatQuery) {
                    var cursor = 0;
                    var currentKey = org.mwg.Constants.NULL_LONG;
                    var lastElemStart = 0;
                    while (cursor < flatQuery.length) {
                        if (flatQuery.charAt(cursor) == org.mwg.Constants.QUERY_KV_SEP) {
                            if (lastElemStart != -1) {
                                currentKey = this._resolver.stringToHash(flatQuery.substring(lastElemStart, cursor).trim(), false);
                            }
                            lastElemStart = cursor + 1;
                        }
                        else if (flatQuery.charAt(cursor) == org.mwg.Constants.QUERY_SEP) {
                            if (currentKey != org.mwg.Constants.NULL_LONG) {
                                this.internal_add(currentKey, flatQuery.substring(lastElemStart, cursor).trim());
                            }
                            currentKey = org.mwg.Constants.NULL_LONG;
                            lastElemStart = cursor + 1;
                        }
                        cursor++;
                    }
                    if (currentKey != org.mwg.Constants.NULL_LONG) {
                        this.internal_add(currentKey, flatQuery.substring(lastElemStart, cursor).trim());
                    }
                    return this;
                };
                CoreQuery.prototype.add = function (attributeName, value) {
                    this.internal_add(this._resolver.stringToHash(attributeName.trim(), false), value);
                    return this;
                };
                CoreQuery.prototype.setWorld = function (initialWorld) {
                    this._world = initialWorld;
                    return this;
                };
                CoreQuery.prototype.world = function () {
                    return this._world;
                };
                CoreQuery.prototype.setTime = function (initialTime) {
                    this._time = initialTime;
                    return this;
                };
                CoreQuery.prototype.time = function () {
                    return this._time;
                };
                CoreQuery.prototype.setIndexName = function (indexName) {
                    this._indexName = indexName;
                    return this;
                };
                CoreQuery.prototype.indexName = function () {
                    return this._indexName;
                };
                CoreQuery.prototype.hash = function () {
                    if (this._hash == null) {
                        this.compute();
                    }
                    return this._hash;
                };
                CoreQuery.prototype.attributes = function () {
                    return this._attributes;
                };
                CoreQuery.prototype.values = function () {
                    return this._values;
                };
                CoreQuery.prototype.internal_add = function (att, val) {
                    if (this.size == this.capacity) {
                        var temp_capacity = this.capacity * 2;
                        var temp_attributes = new Float64Array(temp_capacity);
                        var temp_values = new Array(temp_capacity);
                        java.lang.System.arraycopy(this._attributes, 0, temp_attributes, 0, this.capacity);
                        java.lang.System.arraycopy(this._values, 0, temp_values, 0, this.capacity);
                        this._attributes = temp_attributes;
                        this._values = temp_values;
                        this.capacity = temp_capacity;
                    }
                    this._attributes[this.size] = att;
                    this._values[this.size] = val;
                    this.size++;
                };
                CoreQuery.prototype.compute = function () {
                    for (var i = (this.size - 1); i >= 0; i--) {
                        for (var j = 1; j <= i; j++) {
                            if (this._attributes[j - 1] > this._attributes[j]) {
                                var tempK = this._attributes[j - 1];
                                var tempV = this._values[j - 1];
                                this._attributes[j - 1] = this._attributes[j];
                                this._values[j - 1] = this._values[j];
                                this._attributes[j] = tempK;
                                this._values[j] = tempV;
                            }
                        }
                    }
                    var buf = this._graph.newBuffer();
                    for (var i = 0; i < this.size; i++) {
                        org.mwg.utility.Base64.encodeLongToBuffer(this._attributes[i], buf);
                        var loopValue = this._values[i];
                        if (loopValue != null) {
                            org.mwg.utility.Base64.encodeStringToBuffer(this._values[i], buf);
                        }
                    }
                    this._hash = org.mwg.utility.HashHelper.hashBytes(buf.data());
                    buf.free();
                };
                return CoreQuery;
            }());
            core.CoreQuery = CoreQuery;
            var MWGResolver = (function () {
                function MWGResolver(p_storage, p_space, p_graph) {
                    this._space = p_space;
                    this._storage = p_storage;
                    this._graph = p_graph;
                }
                MWGResolver.prototype.init = function () {
                    this.dictionary = this._space.getAndMark(org.mwg.chunk.ChunkType.STATE_CHUNK, org.mwg.core.CoreConstants.GLOBAL_DICTIONARY_KEY[0], org.mwg.core.CoreConstants.GLOBAL_DICTIONARY_KEY[1], org.mwg.core.CoreConstants.GLOBAL_DICTIONARY_KEY[2]);
                    this.globalWorldOrderChunk = this._space.getAndMark(org.mwg.chunk.ChunkType.WORLD_ORDER_CHUNK, 0, 0, org.mwg.Constants.NULL_LONG);
                };
                MWGResolver.prototype.typeName = function (node) {
                    return this.hashToString(this.typeCode(node));
                };
                MWGResolver.prototype.typeCode = function (node) {
                    var casted = node;
                    var worldOrderChunk = this._space.get(casted._index_worldOrder);
                    if (worldOrderChunk == null) {
                        return org.mwg.Constants.NULL_LONG;
                    }
                    return worldOrderChunk.extra();
                };
                MWGResolver.prototype.initNode = function (node, codeType) {
                    var casted = node;
                    var cacheEntry = this._space.createAndMark(org.mwg.chunk.ChunkType.STATE_CHUNK, node.world(), node.time(), node.id());
                    this._space.notifyUpdate(cacheEntry.index());
                    var superTimeTree = this._space.createAndMark(org.mwg.chunk.ChunkType.TIME_TREE_CHUNK, node.world(), org.mwg.Constants.NULL_LONG, node.id());
                    superTimeTree.insert(node.time());
                    var timeTree = this._space.createAndMark(org.mwg.chunk.ChunkType.TIME_TREE_CHUNK, node.world(), node.time(), node.id());
                    timeTree.insert(node.time());
                    var objectWorldOrder = this._space.createAndMark(org.mwg.chunk.ChunkType.WORLD_ORDER_CHUNK, 0, 0, node.id());
                    objectWorldOrder.put(node.world(), node.time());
                    if (codeType != org.mwg.Constants.NULL_LONG) {
                        objectWorldOrder.setExtra(codeType);
                    }
                    casted._index_stateChunk = cacheEntry.index();
                    casted._index_timeTree = timeTree.index();
                    casted._index_superTimeTree = superTimeTree.index();
                    casted._index_worldOrder = objectWorldOrder.index();
                    casted._world_magic = -1;
                    casted._super_time_magic = -1;
                    casted._time_magic = -1;
                    casted.init();
                };
                MWGResolver.prototype.initWorld = function (parentWorld, childWorld) {
                    this.globalWorldOrderChunk.put(childWorld, parentWorld);
                };
                MWGResolver.prototype.freeNode = function (node) {
                    var casted = node;
                    casted.cacheLock();
                    if (!casted._dead) {
                        this._space.unmark(casted._index_stateChunk);
                        this._space.unmark(casted._index_timeTree);
                        this._space.unmark(casted._index_superTimeTree);
                        this._space.unmark(casted._index_worldOrder);
                        casted._dead = true;
                    }
                    casted.cacheUnlock();
                };
                MWGResolver.prototype.lookup = function (world, time, id, callback) {
                    var _this = this;
                    var selfPointer = this;
                    try {
                        selfPointer._space.getOrLoadAndMark(org.mwg.chunk.ChunkType.WORLD_ORDER_CHUNK, 0, 0, id, function (theNodeWorldOrder) {
                            {
                                if (theNodeWorldOrder == null) {
                                    callback(null);
                                }
                                else {
                                    var closestWorld_1 = selfPointer.resolve_world(_this.globalWorldOrderChunk, theNodeWorldOrder, time, world);
                                    selfPointer._space.getOrLoadAndMark(org.mwg.chunk.ChunkType.TIME_TREE_CHUNK, closestWorld_1, org.mwg.Constants.NULL_LONG, id, function (theNodeSuperTimeTree) {
                                        {
                                            if (theNodeSuperTimeTree == null) {
                                                selfPointer._space.unmark(theNodeWorldOrder.index());
                                                callback(null);
                                            }
                                            else {
                                                var closestSuperTime = theNodeSuperTimeTree.previousOrEqual(time);
                                                if (closestSuperTime == org.mwg.Constants.NULL_LONG) {
                                                    selfPointer._space.unmark(theNodeSuperTimeTree.index());
                                                    selfPointer._space.unmark(theNodeWorldOrder.index());
                                                    callback(null);
                                                    return;
                                                }
                                                selfPointer._space.getOrLoadAndMark(org.mwg.chunk.ChunkType.TIME_TREE_CHUNK, closestWorld_1, closestSuperTime, id, function (theNodeTimeTree) {
                                                    {
                                                        if (theNodeTimeTree == null) {
                                                            selfPointer._space.unmark(theNodeSuperTimeTree.index());
                                                            selfPointer._space.unmark(theNodeWorldOrder.index());
                                                            callback(null);
                                                        }
                                                        else {
                                                            var closestTime_1 = theNodeTimeTree.previousOrEqual(time);
                                                            if (closestTime_1 == org.mwg.Constants.NULL_LONG) {
                                                                selfPointer._space.unmark(theNodeTimeTree.index());
                                                                selfPointer._space.unmark(theNodeSuperTimeTree.index());
                                                                selfPointer._space.unmark(theNodeWorldOrder.index());
                                                                callback(null);
                                                                return;
                                                            }
                                                            selfPointer._space.getOrLoadAndMark(org.mwg.chunk.ChunkType.STATE_CHUNK, closestWorld_1, closestTime_1, id, function (theObjectChunk) {
                                                                {
                                                                    if (theObjectChunk == null) {
                                                                        selfPointer._space.unmark(theNodeTimeTree.index());
                                                                        selfPointer._space.unmark(theNodeSuperTimeTree.index());
                                                                        selfPointer._space.unmark(theNodeWorldOrder.index());
                                                                        callback(null);
                                                                    }
                                                                    else {
                                                                        var castedNodeWorldOrder = theNodeWorldOrder;
                                                                        var extraCode = castedNodeWorldOrder.extra();
                                                                        var resolvedFactory = null;
                                                                        if (extraCode != org.mwg.Constants.NULL_LONG) {
                                                                            resolvedFactory = selfPointer._graph.factoryByCode(extraCode);
                                                                        }
                                                                        var resolvedNode = void 0;
                                                                        if (resolvedFactory == null) {
                                                                            resolvedNode = new org.mwg.core.CoreNode(world, time, id, selfPointer._graph);
                                                                        }
                                                                        else {
                                                                            resolvedNode = resolvedFactory(world, time, id, selfPointer._graph);
                                                                        }
                                                                        resolvedNode._dead = false;
                                                                        resolvedNode._index_stateChunk = theObjectChunk.index();
                                                                        resolvedNode._index_superTimeTree = theNodeSuperTimeTree.index();
                                                                        resolvedNode._index_timeTree = theNodeTimeTree.index();
                                                                        resolvedNode._index_worldOrder = theNodeWorldOrder.index();
                                                                        if (closestWorld_1 == world && closestTime_1 == time) {
                                                                            resolvedNode._world_magic = -1;
                                                                            resolvedNode._super_time_magic = -1;
                                                                            resolvedNode._time_magic = -1;
                                                                        }
                                                                        else {
                                                                            resolvedNode._world_magic = theNodeWorldOrder.magic();
                                                                            resolvedNode._super_time_magic = theNodeSuperTimeTree.magic();
                                                                            resolvedNode._time_magic = theNodeTimeTree.magic();
                                                                        }
                                                                        if (callback != null) {
                                                                            var casted = resolvedNode;
                                                                            callback(casted);
                                                                        }
                                                                    }
                                                                }
                                                            });
                                                        }
                                                    }
                                                });
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                    catch ($ex$) {
                        if ($ex$ instanceof Error) {
                            var e = $ex$;
                            {
                                console.error(e);
                            }
                        }
                        else {
                            throw $ex$;
                        }
                    }
                };
                MWGResolver.prototype.lookupAll_end = function (finalResult, callback, sizeIds, worldOrders, superTimes, times, chunks) {
                    if (worldOrders != null || superTimes != null || times != null || chunks != null) {
                        for (var i = 0; i < sizeIds; i++) {
                            if (finalResult[i] == null) {
                                if (worldOrders != null && worldOrders[i] != null) {
                                    this._space.unmark(worldOrders[i].index());
                                }
                                if (superTimes != null && superTimes[i] != null) {
                                    this._space.unmark(superTimes[i].index());
                                }
                                if (times != null && times[i] != null) {
                                    this._space.unmark(times[i].index());
                                }
                                if (chunks != null && chunks[i] != null) {
                                    this._space.unmark(chunks[i].index());
                                }
                            }
                        }
                    }
                    callback(finalResult);
                };
                MWGResolver.prototype.lookupAll = function (world, time, ids, callback) {
                    var _this = this;
                    var selfPointer = this;
                    var idsSize = ids.length;
                    var finalResult = new Array(idsSize);
                    for (var i = 0; i < idsSize; i++) {
                        finalResult[i] = null;
                    }
                    var isEmpty = [true];
                    var keys = new Float64Array(idsSize * org.mwg.Constants.KEY_SIZE);
                    for (var i = 0; i < idsSize; i++) {
                        isEmpty[0] = false;
                        keys[i * org.mwg.Constants.KEY_SIZE] = org.mwg.chunk.ChunkType.WORLD_ORDER_CHUNK;
                        keys[(i * org.mwg.Constants.KEY_SIZE) + 1] = 0;
                        keys[(i * org.mwg.Constants.KEY_SIZE) + 2] = 0;
                        keys[(i * org.mwg.Constants.KEY_SIZE) + 3] = ids[i];
                    }
                    if (isEmpty[0]) {
                        this.lookupAll_end(finalResult, callback, idsSize, null, null, null, null);
                    }
                    else {
                        selfPointer._space.getOrLoadAndMarkAll(keys, function (theNodeWorldOrders) {
                            {
                                if (theNodeWorldOrders == null) {
                                    _this.lookupAll_end(finalResult, callback, idsSize, null, null, null, null);
                                }
                                else {
                                    isEmpty[0] = true;
                                    for (var i = 0; i < idsSize; i++) {
                                        if (theNodeWorldOrders[i] != null) {
                                            isEmpty[0] = false;
                                            keys[i * org.mwg.Constants.KEY_SIZE] = org.mwg.chunk.ChunkType.TIME_TREE_CHUNK;
                                            keys[(i * org.mwg.Constants.KEY_SIZE) + 1] = selfPointer.resolve_world(_this.globalWorldOrderChunk, theNodeWorldOrders[i], time, world);
                                            keys[(i * org.mwg.Constants.KEY_SIZE) + 2] = org.mwg.Constants.NULL_LONG;
                                        }
                                        else {
                                            keys[i * org.mwg.Constants.KEY_SIZE] = -1;
                                        }
                                    }
                                    if (isEmpty[0]) {
                                        _this.lookupAll_end(finalResult, callback, idsSize, theNodeWorldOrders, null, null, null);
                                    }
                                    else {
                                        selfPointer._space.getOrLoadAndMarkAll(keys, function (theNodeSuperTimeTrees) {
                                            {
                                                if (theNodeSuperTimeTrees == null) {
                                                    _this.lookupAll_end(finalResult, callback, idsSize, theNodeWorldOrders, null, null, null);
                                                }
                                                else {
                                                    isEmpty[0] = true;
                                                    for (var i = 0; i < idsSize; i++) {
                                                        if (theNodeSuperTimeTrees[i] != null) {
                                                            var closestSuperTime = theNodeSuperTimeTrees[i].previousOrEqual(time);
                                                            if (closestSuperTime == org.mwg.Constants.NULL_LONG) {
                                                                keys[i * org.mwg.Constants.KEY_SIZE] = -1;
                                                            }
                                                            else {
                                                                isEmpty[0] = false;
                                                                keys[(i * org.mwg.Constants.KEY_SIZE) + 2] = closestSuperTime;
                                                            }
                                                        }
                                                        else {
                                                            keys[i * org.mwg.Constants.KEY_SIZE] = -1;
                                                        }
                                                    }
                                                    if (isEmpty[0]) {
                                                        _this.lookupAll_end(finalResult, callback, idsSize, theNodeWorldOrders, theNodeSuperTimeTrees, null, null);
                                                    }
                                                    else {
                                                        selfPointer._space.getOrLoadAndMarkAll(keys, function (theNodeTimeTrees) {
                                                            {
                                                                if (theNodeTimeTrees == null) {
                                                                    _this.lookupAll_end(finalResult, callback, idsSize, theNodeWorldOrders, theNodeSuperTimeTrees, null, null);
                                                                }
                                                                else {
                                                                    isEmpty[0] = true;
                                                                    for (var i = 0; i < idsSize; i++) {
                                                                        if (theNodeTimeTrees[i] != null) {
                                                                            var closestTime = theNodeTimeTrees[i].previousOrEqual(time);
                                                                            if (closestTime == org.mwg.Constants.NULL_LONG) {
                                                                                keys[i * org.mwg.Constants.KEY_SIZE] = -1;
                                                                            }
                                                                            else {
                                                                                isEmpty[0] = false;
                                                                                keys[(i * org.mwg.Constants.KEY_SIZE)] = org.mwg.chunk.ChunkType.STATE_CHUNK;
                                                                                keys[(i * org.mwg.Constants.KEY_SIZE) + 2] = closestTime;
                                                                            }
                                                                        }
                                                                        else {
                                                                            keys[i * org.mwg.Constants.KEY_SIZE] = -1;
                                                                        }
                                                                    }
                                                                    if (isEmpty[0]) {
                                                                        _this.lookupAll_end(finalResult, callback, idsSize, theNodeWorldOrders, theNodeSuperTimeTrees, theNodeTimeTrees, null);
                                                                    }
                                                                    else {
                                                                        selfPointer._space.getOrLoadAndMarkAll(keys, function (theObjectChunks) {
                                                                            {
                                                                                if (theObjectChunks == null) {
                                                                                    _this.lookupAll_end(finalResult, callback, idsSize, theNodeWorldOrders, theNodeSuperTimeTrees, theNodeTimeTrees, null);
                                                                                }
                                                                                else {
                                                                                    for (var i = 0; i < idsSize; i++) {
                                                                                        if (theObjectChunks[i] != null) {
                                                                                            var castedNodeWorldOrder = theNodeWorldOrders[i];
                                                                                            var extraCode = castedNodeWorldOrder.extra();
                                                                                            var resolvedFactory = null;
                                                                                            if (extraCode != org.mwg.Constants.NULL_LONG) {
                                                                                                resolvedFactory = selfPointer._graph.factoryByCode(extraCode);
                                                                                            }
                                                                                            var resolvedNode = void 0;
                                                                                            if (resolvedFactory == null) {
                                                                                                resolvedNode = new org.mwg.core.CoreNode(world, time, ids[i], selfPointer._graph);
                                                                                            }
                                                                                            else {
                                                                                                resolvedNode = resolvedFactory(world, time, ids[i], selfPointer._graph);
                                                                                            }
                                                                                            resolvedNode._dead = false;
                                                                                            resolvedNode._index_stateChunk = theObjectChunks[i].index();
                                                                                            resolvedNode._index_superTimeTree = theNodeSuperTimeTrees[i].index();
                                                                                            resolvedNode._index_timeTree = theNodeTimeTrees[i].index();
                                                                                            resolvedNode._index_worldOrder = theNodeWorldOrders[i].index();
                                                                                            if (theObjectChunks[i].world() == world && theObjectChunks[i].time() == time) {
                                                                                                resolvedNode._world_magic = -1;
                                                                                                resolvedNode._super_time_magic = -1;
                                                                                                resolvedNode._time_magic = -1;
                                                                                            }
                                                                                            else {
                                                                                                resolvedNode._world_magic = theNodeWorldOrders[i].magic();
                                                                                                resolvedNode._super_time_magic = theNodeSuperTimeTrees[i].magic();
                                                                                                resolvedNode._time_magic = theNodeTimeTrees[i].magic();
                                                                                            }
                                                                                            finalResult[i] = resolvedNode;
                                                                                        }
                                                                                    }
                                                                                    _this.lookupAll_end(finalResult, callback, idsSize, theNodeWorldOrders, theNodeSuperTimeTrees, theNodeTimeTrees, theObjectChunks);
                                                                                }
                                                                            }
                                                                        });
                                                                    }
                                                                }
                                                            }
                                                        });
                                                    }
                                                }
                                            }
                                        });
                                    }
                                }
                            }
                        });
                    }
                };
                MWGResolver.prototype.resolve_world = function (globalWorldOrder, nodeWorldOrder, timeToResolve, originWorld) {
                    if (globalWorldOrder == null || nodeWorldOrder == null) {
                        return originWorld;
                    }
                    var currentUniverse = originWorld;
                    var previousUniverse = org.mwg.Constants.NULL_LONG;
                    var divergenceTime = nodeWorldOrder.get(currentUniverse);
                    while (currentUniverse != previousUniverse) {
                        if (divergenceTime != org.mwg.Constants.NULL_LONG && divergenceTime <= timeToResolve) {
                            return currentUniverse;
                        }
                        previousUniverse = currentUniverse;
                        currentUniverse = globalWorldOrder.get(currentUniverse);
                        divergenceTime = nodeWorldOrder.get(currentUniverse);
                    }
                    return originWorld;
                };
                MWGResolver.prototype.getOrLoadAndMarkAll = function (types, keys, callback) {
                    var nbKeys = keys.length / MWGResolver.KEY_SIZE;
                    var toLoadIndexes = [];
                    var nbElem = 0;
                    var result = new Array(nbKeys);
                    for (var i = 0; i < nbKeys; i++) {
                        if (keys[i * MWGResolver.KEY_SIZE] == org.mwg.core.CoreConstants.NULL_KEY[0] && keys[i * MWGResolver.KEY_SIZE + 1] == org.mwg.core.CoreConstants.NULL_KEY[1] && keys[i * MWGResolver.KEY_SIZE + 2] == org.mwg.core.CoreConstants.NULL_KEY[2]) {
                            toLoadIndexes[i] = false;
                            result[i] = null;
                        }
                        else {
                            result[i] = this._space.getAndMark(types[i], keys[i * MWGResolver.KEY_SIZE], keys[i * MWGResolver.KEY_SIZE + 1], keys[i * MWGResolver.KEY_SIZE + 2]);
                            if (result[i] == null) {
                                toLoadIndexes[i] = true;
                                nbElem++;
                            }
                            else {
                                toLoadIndexes[i] = false;
                            }
                        }
                    }
                    if (nbElem == 0) {
                        callback(result);
                    }
                    else {
                        var keysToLoad_1 = this._graph.newBuffer();
                        var reverseIndex_1 = new Int32Array(nbElem);
                        var lastInsertedIndex = 0;
                        for (var i = 0; i < nbKeys; i++) {
                            if (toLoadIndexes[i]) {
                                reverseIndex_1[lastInsertedIndex] = i;
                                if (lastInsertedIndex != 0) {
                                    keysToLoad_1.write(org.mwg.core.CoreConstants.BUFFER_SEP);
                                }
                                org.mwg.utility.KeyHelper.keyToBuffer(keysToLoad_1, types[i], keys[i * MWGResolver.KEY_SIZE], keys[i * MWGResolver.KEY_SIZE + 1], keys[i * MWGResolver.KEY_SIZE + 2]);
                                lastInsertedIndex = lastInsertedIndex + 1;
                            }
                        }
                        var selfPointer_3 = this;
                        this._storage.get(keysToLoad_1, function (fromDbBuffers) {
                            {
                                keysToLoad_1.free();
                                var it = fromDbBuffers.iterator();
                                var i = 0;
                                while (it.hasNext()) {
                                    var reversedIndex = reverseIndex_1[i];
                                    var view = it.next();
                                    if (view.length() > 0) {
                                        result[reversedIndex] = selfPointer_3._space.createAndMark(types[reversedIndex], keys[reversedIndex * org.mwg.core.MWGResolver.KEY_SIZE], keys[reversedIndex * org.mwg.core.MWGResolver.KEY_SIZE + 1], keys[reversedIndex * org.mwg.core.MWGResolver.KEY_SIZE + 2]);
                                        result[reversedIndex].load(view);
                                    }
                                    else {
                                        result[reversedIndex] = null;
                                    }
                                    i++;
                                }
                                fromDbBuffers.free();
                                callback(result);
                            }
                        });
                    }
                };
                MWGResolver.prototype.resolveState = function (node) {
                    return this.internal_resolveState(node, true);
                };
                MWGResolver.prototype.internal_resolveState = function (node, safe) {
                    var castedNode = node;
                    var stateResult = null;
                    if (safe) {
                        castedNode.cacheLock();
                    }
                    if (castedNode._dead) {
                        if (safe) {
                            castedNode.cacheUnlock();
                        }
                        throw new Error(org.mwg.core.CoreConstants.DEAD_NODE_ERROR + " node id: " + node.id());
                    }
                    if (castedNode._world_magic == -1 && castedNode._time_magic == -1 && castedNode._super_time_magic == -1) {
                        stateResult = this._space.get(castedNode._index_stateChunk);
                    }
                    else {
                        var nodeWorldOrder = this._space.get(castedNode._index_worldOrder);
                        var nodeSuperTimeTree = this._space.get(castedNode._index_superTimeTree);
                        var nodeTimeTree = this._space.get(castedNode._index_timeTree);
                        if (nodeWorldOrder != null && nodeSuperTimeTree != null && nodeTimeTree != null) {
                            if (castedNode._world_magic == nodeWorldOrder.magic() && castedNode._super_time_magic == nodeSuperTimeTree.magic() && castedNode._time_magic == nodeTimeTree.magic()) {
                                stateResult = this._space.get(castedNode._index_stateChunk);
                            }
                            else {
                                if (safe) {
                                    nodeWorldOrder.lock();
                                }
                                var nodeTime = castedNode.time();
                                var nodeId = castedNode.id();
                                var nodeWorld = castedNode.world();
                                var resolvedWorld = this.resolve_world(this.globalWorldOrderChunk, nodeWorldOrder, nodeTime, nodeWorld);
                                if (resolvedWorld != nodeSuperTimeTree.world()) {
                                    var tempNodeSuperTimeTree = this._space.getAndMark(org.mwg.chunk.ChunkType.TIME_TREE_CHUNK, resolvedWorld, org.mwg.core.CoreConstants.NULL_LONG, nodeId);
                                    if (tempNodeSuperTimeTree != null) {
                                        this._space.unmark(nodeSuperTimeTree.index());
                                        nodeSuperTimeTree = tempNodeSuperTimeTree;
                                    }
                                }
                                var resolvedSuperTime = nodeSuperTimeTree.previousOrEqual(nodeTime);
                                if (resolvedSuperTime != nodeTimeTree.time()) {
                                    var tempNodeTimeTree = this._space.getAndMark(org.mwg.chunk.ChunkType.TIME_TREE_CHUNK, resolvedWorld, resolvedSuperTime, nodeId);
                                    if (tempNodeTimeTree != null) {
                                        this._space.unmark(nodeTimeTree.index());
                                        nodeTimeTree = tempNodeTimeTree;
                                    }
                                }
                                var resolvedTime = nodeTimeTree.previousOrEqual(nodeTime);
                                if (resolvedWorld == nodeWorld && resolvedTime == nodeTime) {
                                    castedNode._world_magic = -1;
                                    castedNode._time_magic = -1;
                                    castedNode._super_time_magic = -1;
                                }
                                else {
                                    castedNode._world_magic = nodeWorldOrder.magic();
                                    castedNode._time_magic = nodeTimeTree.magic();
                                    castedNode._super_time_magic = nodeSuperTimeTree.magic();
                                    castedNode._index_superTimeTree = nodeSuperTimeTree.index();
                                    castedNode._index_timeTree = nodeTimeTree.index();
                                }
                                stateResult = this._space.get(castedNode._index_stateChunk);
                                if (resolvedWorld != stateResult.world() || resolvedTime != stateResult.time()) {
                                    var tempNodeState = this._space.getAndMark(org.mwg.chunk.ChunkType.STATE_CHUNK, resolvedWorld, resolvedTime, nodeId);
                                    if (tempNodeState != null) {
                                        this._space.unmark(stateResult.index());
                                        stateResult = tempNodeState;
                                    }
                                }
                                castedNode._index_stateChunk = stateResult.index();
                                if (safe) {
                                    nodeWorldOrder.unlock();
                                }
                            }
                        }
                    }
                    if (safe) {
                        castedNode.cacheUnlock();
                    }
                    return stateResult;
                };
                MWGResolver.prototype.alignState = function (node) {
                    var castedNode = node;
                    castedNode.cacheLock();
                    if (castedNode._dead) {
                        castedNode.cacheUnlock();
                        throw new Error(org.mwg.core.CoreConstants.DEAD_NODE_ERROR + " node id: " + node.id());
                    }
                    if (castedNode._world_magic == -1 && castedNode._time_magic == -1 && castedNode._super_time_magic == -1) {
                        var currentEntry = this._space.get(castedNode._index_stateChunk);
                        if (currentEntry != null) {
                            castedNode.cacheUnlock();
                            return currentEntry;
                        }
                    }
                    var nodeWorldOrder = this._space.get(castedNode._index_worldOrder);
                    if (nodeWorldOrder == null) {
                        castedNode.cacheUnlock();
                        return null;
                    }
                    nodeWorldOrder.lock();
                    var nodeWorld = node.world();
                    var nodeTime = node.time();
                    var nodeId = node.id();
                    var previouStateChunk = this.internal_resolveState(node, false);
                    if (castedNode._world_magic == -1 && castedNode._time_magic == -1 && castedNode._super_time_magic == -1) {
                        nodeWorldOrder.unlock();
                        castedNode.cacheUnlock();
                        return previouStateChunk;
                    }
                    var clonedState = this._space.createAndMark(org.mwg.chunk.ChunkType.STATE_CHUNK, nodeWorld, nodeTime, nodeId);
                    clonedState.loadFrom(previouStateChunk);
                    castedNode._world_magic = -1;
                    castedNode._super_time_magic = -1;
                    castedNode._time_magic = -1;
                    castedNode._index_stateChunk = clonedState.index();
                    this._space.unmark(previouStateChunk.index());
                    if (previouStateChunk.world() == nodeWorld || nodeWorldOrder.get(nodeWorld) != org.mwg.core.CoreConstants.NULL_LONG) {
                        var superTimeTree = this._space.get(castedNode._index_superTimeTree);
                        var timeTree = this._space.get(castedNode._index_timeTree);
                        var superTreeSize = superTimeTree.size();
                        var threshold = org.mwg.core.CoreConstants.SCALE_1 * 2;
                        if (superTreeSize > threshold) {
                            threshold = org.mwg.core.CoreConstants.SCALE_2 * 2;
                        }
                        if (superTreeSize > threshold) {
                            threshold = org.mwg.core.CoreConstants.SCALE_3 * 2;
                        }
                        if (superTreeSize > threshold) {
                            threshold = org.mwg.core.CoreConstants.SCALE_4 * 2;
                        }
                        timeTree.insert(nodeTime);
                        if (timeTree.size() == threshold) {
                            var medianPoint_1 = new Float64Array([-1]);
                            timeTree.range(org.mwg.core.CoreConstants.BEGINNING_OF_TIME, org.mwg.core.CoreConstants.END_OF_TIME, timeTree.size() / 2, function (t) {
                                {
                                    medianPoint_1[0] = t;
                                }
                            });
                            var rightTree = this._space.createAndMark(org.mwg.chunk.ChunkType.TIME_TREE_CHUNK, nodeWorld, medianPoint_1[0], nodeId);
                            var finalRightTree_1 = rightTree;
                            timeTree.range(org.mwg.core.CoreConstants.BEGINNING_OF_TIME, org.mwg.core.CoreConstants.END_OF_TIME, timeTree.size() / 2, function (t) {
                                {
                                    finalRightTree_1.unsafe_insert(t);
                                }
                            });
                            this._space.notifyUpdate(finalRightTree_1.index());
                            superTimeTree.insert(medianPoint_1[0]);
                            timeTree.clearAt(medianPoint_1[0]);
                            if (nodeTime < medianPoint_1[0]) {
                                this._space.unmark(rightTree.index());
                            }
                            else {
                                castedNode._index_timeTree = finalRightTree_1.index();
                                this._space.unmark(timeTree.index());
                            }
                        }
                    }
                    else {
                        var newSuperTimeTree = this._space.createAndMark(org.mwg.chunk.ChunkType.TIME_TREE_CHUNK, nodeWorld, org.mwg.core.CoreConstants.NULL_LONG, nodeId);
                        newSuperTimeTree.insert(nodeTime);
                        var newTimeTree = this._space.createAndMark(org.mwg.chunk.ChunkType.TIME_TREE_CHUNK, nodeWorld, nodeTime, nodeId);
                        newTimeTree.insert(nodeTime);
                        nodeWorldOrder.put(nodeWorld, nodeTime);
                        this._space.unmark(castedNode._index_timeTree);
                        this._space.unmark(castedNode._index_superTimeTree);
                        castedNode._index_timeTree = newTimeTree.index();
                        castedNode._index_superTimeTree = newSuperTimeTree.index();
                    }
                    nodeWorldOrder.unlock();
                    castedNode.cacheUnlock();
                    return clonedState;
                };
                MWGResolver.prototype.newState = function (node, world, time) {
                    var castedNode = node;
                    var resolved;
                    castedNode.cacheLock();
                    var fakeNode = new org.mwg.core.CoreNode(world, time, node.id(), node.graph());
                    fakeNode._index_worldOrder = castedNode._index_worldOrder;
                    fakeNode._index_superTimeTree = castedNode._index_superTimeTree;
                    fakeNode._index_timeTree = castedNode._index_timeTree;
                    fakeNode._index_stateChunk = castedNode._index_stateChunk;
                    fakeNode._time_magic = castedNode._time_magic;
                    fakeNode._super_time_magic = castedNode._super_time_magic;
                    fakeNode._world_magic = castedNode._world_magic;
                    resolved = this.alignState(fakeNode);
                    castedNode._index_worldOrder = fakeNode._index_worldOrder;
                    castedNode._index_superTimeTree = fakeNode._index_superTimeTree;
                    castedNode._index_timeTree = fakeNode._index_timeTree;
                    castedNode._index_stateChunk = fakeNode._index_stateChunk;
                    castedNode._time_magic = fakeNode._time_magic;
                    castedNode._super_time_magic = fakeNode._super_time_magic;
                    castedNode._world_magic = fakeNode._world_magic;
                    castedNode.cacheUnlock();
                    return resolved;
                };
                MWGResolver.prototype.resolveTimepoints = function (node, beginningOfSearch, endOfSearch, callback) {
                    var selfPointer = this;
                    this._space.getOrLoadAndMark(org.mwg.chunk.ChunkType.WORLD_ORDER_CHUNK, 0, 0, node.id(), function (resolved) {
                        {
                            if (resolved == null) {
                                callback(new Float64Array(0));
                                return;
                            }
                            var objectWorldOrder = resolved;
                            var collectionSize = new Int32Array([org.mwg.core.CoreConstants.MAP_INITIAL_CAPACITY]);
                            var collectedWorlds = [new Float64Array(collectionSize[0])];
                            var collectedIndex = 0;
                            var currentWorld = node.world();
                            while (currentWorld != org.mwg.core.CoreConstants.NULL_LONG) {
                                var divergenceTimepoint = objectWorldOrder.get(currentWorld);
                                if (divergenceTimepoint != org.mwg.core.CoreConstants.NULL_LONG) {
                                    if (divergenceTimepoint <= beginningOfSearch) {
                                        collectedWorlds[0][collectedIndex] = currentWorld;
                                        collectedIndex++;
                                        break;
                                    }
                                    else if (divergenceTimepoint > endOfSearch) {
                                        currentWorld = selfPointer.globalWorldOrderChunk.get(currentWorld);
                                    }
                                    else {
                                        collectedWorlds[0][collectedIndex] = currentWorld;
                                        collectedIndex++;
                                        if (collectedIndex == collectionSize[0]) {
                                            var temp_collectedWorlds = new Float64Array(collectionSize[0] * 2);
                                            java.lang.System.arraycopy(collectedWorlds[0], 0, temp_collectedWorlds, 0, collectionSize[0]);
                                            collectedWorlds[0] = temp_collectedWorlds;
                                            collectionSize[0] = collectionSize[0] * 2;
                                        }
                                        currentWorld = selfPointer.globalWorldOrderChunk.get(currentWorld);
                                    }
                                }
                                else {
                                    currentWorld = selfPointer.globalWorldOrderChunk.get(currentWorld);
                                }
                            }
                            selfPointer.resolveTimepointsFromWorlds(selfPointer.globalWorldOrderChunk, objectWorldOrder, node, beginningOfSearch, endOfSearch, collectedWorlds[0], collectedIndex, callback);
                        }
                    });
                };
                MWGResolver.prototype.resolveTimepointsFromWorlds = function (globalWorldOrder, objectWorldOrder, node, beginningOfSearch, endOfSearch, collectedWorlds, collectedWorldsSize, callback) {
                    var selfPointer = this;
                    var timeTreeKeys = new Float64Array(collectedWorldsSize * 3);
                    var types = new Int8Array(collectedWorldsSize);
                    for (var i = 0; i < collectedWorldsSize; i++) {
                        timeTreeKeys[i * 3] = collectedWorlds[i];
                        timeTreeKeys[i * 3 + 1] = org.mwg.core.CoreConstants.NULL_LONG;
                        timeTreeKeys[i * 3 + 2] = node.id();
                        types[i] = org.mwg.chunk.ChunkType.TIME_TREE_CHUNK;
                    }
                    this.getOrLoadAndMarkAll(types, timeTreeKeys, function (superTimeTrees) {
                        {
                            if (superTimeTrees == null) {
                                selfPointer._space.unmark(objectWorldOrder.index());
                                callback(new Float64Array(0));
                            }
                            else {
                                var collectedSize_1 = new Int32Array([org.mwg.core.CoreConstants.MAP_INITIAL_CAPACITY]);
                                var collectedSuperTimes_1 = [new Float64Array(collectedSize_1[0])];
                                var collectedSuperTimesAssociatedWorlds_1 = [new Float64Array(collectedSize_1[0])];
                                var insert_index_1 = new Int32Array([0]);
                                var previousDivergenceTime = endOfSearch;
                                var _loop_2 = function(i) {
                                    var timeTree = superTimeTrees[i];
                                    if (timeTree != null) {
                                        var currentDivergenceTime = objectWorldOrder.get(collectedWorlds[i]);
                                        var finalPreviousDivergenceTime_1 = previousDivergenceTime;
                                        timeTree.range(currentDivergenceTime, previousDivergenceTime, org.mwg.core.CoreConstants.END_OF_TIME, function (t) {
                                            {
                                                if (t != finalPreviousDivergenceTime_1) {
                                                    collectedSuperTimes_1[0][insert_index_1[0]] = t;
                                                    collectedSuperTimesAssociatedWorlds_1[0][insert_index_1[0]] = timeTree.world();
                                                    insert_index_1[0]++;
                                                    if (collectedSize_1[0] == insert_index_1[0]) {
                                                        var temp_collectedSuperTimes = new Float64Array(collectedSize_1[0] * 2);
                                                        var temp_collectedSuperTimesAssociatedWorlds = new Float64Array(collectedSize_1[0] * 2);
                                                        java.lang.System.arraycopy(collectedSuperTimes_1[0], 0, temp_collectedSuperTimes, 0, collectedSize_1[0]);
                                                        java.lang.System.arraycopy(collectedSuperTimesAssociatedWorlds_1[0], 0, temp_collectedSuperTimesAssociatedWorlds, 0, collectedSize_1[0]);
                                                        collectedSuperTimes_1[0] = temp_collectedSuperTimes;
                                                        collectedSuperTimesAssociatedWorlds_1[0] = temp_collectedSuperTimesAssociatedWorlds;
                                                        collectedSize_1[0] = collectedSize_1[0] * 2;
                                                    }
                                                }
                                            }
                                        });
                                        previousDivergenceTime = currentDivergenceTime;
                                    }
                                    selfPointer._space.unmark(timeTree.index());
                                };
                                for (var i = 0; i < collectedWorldsSize; i++) {
                                    _loop_2(i);
                                }
                                selfPointer.resolveTimepointsFromSuperTimes(objectWorldOrder, node, beginningOfSearch, endOfSearch, collectedSuperTimesAssociatedWorlds_1[0], collectedSuperTimes_1[0], insert_index_1[0], callback);
                            }
                        }
                    });
                };
                MWGResolver.prototype.resolveTimepointsFromSuperTimes = function (objectWorldOrder, node, beginningOfSearch, endOfSearch, collectedWorlds, collectedSuperTimes, collectedSize, callback) {
                    var selfPointer = this;
                    var timeTreeKeys = new Float64Array(collectedSize * 3);
                    var types = new Int8Array(collectedSize);
                    for (var i = 0; i < collectedSize; i++) {
                        timeTreeKeys[i * 3] = collectedWorlds[i];
                        timeTreeKeys[i * 3 + 1] = collectedSuperTimes[i];
                        timeTreeKeys[i * 3 + 2] = node.id();
                        types[i] = org.mwg.chunk.ChunkType.TIME_TREE_CHUNK;
                    }
                    this.getOrLoadAndMarkAll(types, timeTreeKeys, function (timeTrees) {
                        {
                            if (timeTrees == null) {
                                selfPointer._space.unmark(objectWorldOrder.index());
                                callback(new Float64Array(0));
                            }
                            else {
                                var collectedTimesSize_1 = new Int32Array([org.mwg.core.CoreConstants.MAP_INITIAL_CAPACITY]);
                                var collectedTimes_1 = [new Float64Array(collectedTimesSize_1[0])];
                                var insert_index_2 = new Int32Array([0]);
                                var previousDivergenceTime = endOfSearch;
                                var _loop_3 = function(i) {
                                    var timeTree = timeTrees[i];
                                    if (timeTree != null) {
                                        var currentDivergenceTime = objectWorldOrder.get(collectedWorlds[i]);
                                        if (currentDivergenceTime < beginningOfSearch) {
                                            currentDivergenceTime = beginningOfSearch;
                                        }
                                        var finalPreviousDivergenceTime_2 = previousDivergenceTime;
                                        timeTree.range(currentDivergenceTime, previousDivergenceTime, org.mwg.core.CoreConstants.END_OF_TIME, function (t) {
                                            {
                                                if (t != finalPreviousDivergenceTime_2) {
                                                    collectedTimes_1[0][insert_index_2[0]] = t;
                                                    insert_index_2[0]++;
                                                    if (collectedTimesSize_1[0] == insert_index_2[0]) {
                                                        var temp_collectedTimes = new Float64Array(collectedTimesSize_1[0] * 2);
                                                        java.lang.System.arraycopy(collectedTimes_1[0], 0, temp_collectedTimes, 0, collectedTimesSize_1[0]);
                                                        collectedTimes_1[0] = temp_collectedTimes;
                                                        collectedTimesSize_1[0] = collectedTimesSize_1[0] * 2;
                                                    }
                                                }
                                            }
                                        });
                                        if (i < collectedSize - 1) {
                                            if (collectedWorlds[i + 1] != collectedWorlds[i]) {
                                                previousDivergenceTime = currentDivergenceTime;
                                            }
                                        }
                                    }
                                    selfPointer._space.unmark(timeTree.index());
                                };
                                for (var i = 0; i < collectedSize; i++) {
                                    _loop_3(i);
                                }
                                if (insert_index_2[0] != collectedTimesSize_1[0]) {
                                    var tempTimeline = new Float64Array(insert_index_2[0]);
                                    java.lang.System.arraycopy(collectedTimes_1[0], 0, tempTimeline, 0, insert_index_2[0]);
                                    collectedTimes_1[0] = tempTimeline;
                                }
                                selfPointer._space.unmark(objectWorldOrder.index());
                                callback(collectedTimes_1[0]);
                            }
                        }
                    });
                };
                MWGResolver.prototype.stringToHash = function (name, insertIfNotExists) {
                    var hash = org.mwg.utility.HashHelper.hash(name);
                    if (insertIfNotExists) {
                        var dictionaryIndex = this.dictionary.get(0);
                        if (dictionaryIndex == null) {
                            dictionaryIndex = this.dictionary.getOrCreate(0, org.mwg.Type.STRING_TO_LONG_MAP);
                        }
                        if (!dictionaryIndex.containsHash(hash)) {
                            dictionaryIndex.put(name, hash);
                        }
                    }
                    return hash;
                };
                MWGResolver.prototype.hashToString = function (key) {
                    var dictionaryIndex = this.dictionary.get(0);
                    if (dictionaryIndex != null) {
                        return dictionaryIndex.getByHash(key);
                    }
                    return null;
                };
                MWGResolver.KEY_SIZE = 3;
                return MWGResolver;
            }());
            core.MWGResolver = MWGResolver;
            var chunk;
            (function (chunk_2) {
                var heap;
                (function (heap) {
                    var HeapAtomicByteArray = (function () {
                        function HeapAtomicByteArray(initialSize) {
                            this._back = new Int8Array(initialSize);
                        }
                        HeapAtomicByteArray.prototype.get = function (index) {
                            return this._back[index];
                        };
                        HeapAtomicByteArray.prototype.set = function (index, value) {
                            this._back[index] = value;
                        };
                        return HeapAtomicByteArray;
                    }());
                    heap.HeapAtomicByteArray = HeapAtomicByteArray;
                    var HeapChunkSpace = (function () {
                        function HeapChunkSpace(initialCapacity, p_graph) {
                            this._graph = p_graph;
                            this._maxEntries = initialCapacity;
                            this._hashEntries = initialCapacity * HeapChunkSpace.HASH_LOAD_FACTOR;
                            this._lru = new org.mwg.core.chunk.heap.HeapFixedStack(initialCapacity, true);
                            this._dirtiesStack = new org.mwg.core.chunk.heap.HeapFixedStack(initialCapacity, false);
                            this._hashNext = new java.util.concurrent.atomic.AtomicIntegerArray(initialCapacity);
                            this._hash = new java.util.concurrent.atomic.AtomicIntegerArray(this._hashEntries);
                            for (var i = 0; i < initialCapacity; i++) {
                                this._hashNext.set(i, -1);
                            }
                            for (var i = 0; i < this._hashEntries; i++) {
                                this._hash.set(i, -1);
                            }
                            this._chunkValues = new java.util.concurrent.atomic.AtomicReferenceArray(initialCapacity);
                            this._chunkWorlds = new java.util.concurrent.atomic.AtomicLongArray(this._maxEntries);
                            this._chunkTimes = new java.util.concurrent.atomic.AtomicLongArray(this._maxEntries);
                            this._chunkIds = new java.util.concurrent.atomic.AtomicLongArray(this._maxEntries);
                            this._chunkTypes = new org.mwg.core.chunk.heap.HeapAtomicByteArray(this._maxEntries);
                            this._chunkMarks = new java.util.concurrent.atomic.AtomicLongArray(this._maxEntries);
                            for (var i = 0; i < this._maxEntries; i++) {
                                this._chunkMarks.set(i, 0);
                            }
                        }
                        HeapChunkSpace.prototype.graph = function () {
                            return this._graph;
                        };
                        HeapChunkSpace.prototype.worldByIndex = function (index) {
                            return this._chunkWorlds.get(index);
                        };
                        HeapChunkSpace.prototype.timeByIndex = function (index) {
                            return this._chunkTimes.get(index);
                        };
                        HeapChunkSpace.prototype.idByIndex = function (index) {
                            return this._chunkIds.get(index);
                        };
                        HeapChunkSpace.prototype.getAndMark = function (type, world, time, id) {
                            var index = org.mwg.utility.HashHelper.tripleHash(type, world, time, id, this._hashEntries);
                            var m = this._hash.get(index);
                            var found = -1;
                            while (m != -1) {
                                if (this._chunkTypes.get(m) == type && this._chunkWorlds.get(m) == world && this._chunkTimes.get(m) == time && this._chunkIds.get(m) == id) {
                                    if (this.mark(m) > 0) {
                                        found = m;
                                    }
                                    break;
                                }
                                else {
                                    m = this._hashNext.get(m);
                                }
                            }
                            if (found != -1) {
                                return this._chunkValues.get(found);
                            }
                            else {
                                return null;
                            }
                        };
                        HeapChunkSpace.prototype.get = function (index) {
                            return this._chunkValues.get(index);
                        };
                        HeapChunkSpace.prototype.getOrLoadAndMark = function (type, world, time, id, callback) {
                            var _this = this;
                            var fromMemory = this.getAndMark(type, world, time, id);
                            if (fromMemory != null) {
                                callback(fromMemory);
                            }
                            else {
                                var keys_2 = this.graph().newBuffer();
                                org.mwg.utility.KeyHelper.keyToBuffer(keys_2, type, world, time, id);
                                this.graph().storage().get(keys_2, function (result) {
                                    {
                                        if (result != null && result.length() > 0) {
                                            var loadedChunk = _this.createAndMark(type, world, time, id);
                                            loadedChunk.load(result);
                                            result.free();
                                            callback(loadedChunk);
                                        }
                                        else {
                                            keys_2.free();
                                            callback(null);
                                        }
                                    }
                                });
                            }
                        };
                        HeapChunkSpace.prototype.getOrLoadAndMarkAll = function (keys, callback) {
                            var _this = this;
                            var querySize = keys.length / org.mwg.Constants.KEY_SIZE;
                            var finalResult = new Array(querySize);
                            var reverse = null;
                            var reverseIndex = 0;
                            var toLoadKeys = null;
                            for (var i = 0; i < querySize; i++) {
                                var offset = i * org.mwg.Constants.KEY_SIZE;
                                var loopType = keys[offset];
                                if (loopType != -1) {
                                    var fromMemory = this.getAndMark(keys[offset], keys[offset + 1], keys[offset + 2], keys[offset + 3]);
                                    if (fromMemory != null) {
                                        finalResult[i] = fromMemory;
                                    }
                                    else {
                                        if (reverse == null) {
                                            reverse = new Int32Array(querySize);
                                            toLoadKeys = this.graph().newBuffer();
                                        }
                                        reverse[i] = reverseIndex;
                                        if (reverseIndex != 0) {
                                            toLoadKeys.write(org.mwg.Constants.BUFFER_SEP);
                                        }
                                        org.mwg.utility.KeyHelper.keyToBuffer(toLoadKeys, keys[offset], keys[offset + 1], keys[offset + 2], keys[offset + 3]);
                                        reverseIndex++;
                                    }
                                }
                                else {
                                    finalResult[i] = null;
                                }
                            }
                            if (reverse != null) {
                                var finalReverse_1 = reverse;
                                this.graph().storage().get(toLoadKeys, function (loadAllResult) {
                                    {
                                        var it = loadAllResult.iterator();
                                        var i = 0;
                                        while (it.hasNext()) {
                                            var view = it.next();
                                            var reversedIndex = finalReverse_1[i];
                                            var reversedOffset = reversedIndex * org.mwg.Constants.KEY_SIZE;
                                            if (view.length() > 0) {
                                                var loadedChunk = _this.createAndMark(keys[reversedOffset], keys[reversedOffset + 1], keys[reversedOffset + 2], keys[reversedOffset + 3]);
                                                loadedChunk.load(view);
                                                finalResult[reversedIndex] = loadedChunk;
                                            }
                                            else {
                                                finalResult[reversedIndex] = null;
                                            }
                                            i++;
                                        }
                                        loadAllResult.free();
                                        callback(finalResult);
                                    }
                                });
                            }
                            else {
                                callback(finalResult);
                            }
                        };
                        HeapChunkSpace.prototype.mark = function (index) {
                            var castedIndex = index;
                            var before;
                            var after;
                            do {
                                before = this._chunkMarks.get(castedIndex);
                                if (before != -1) {
                                    after = before + 1;
                                }
                                else {
                                    after = before;
                                }
                            } while (!this._chunkMarks.compareAndSet(castedIndex, before, after));
                            if (before == 0 && after == 1) {
                                this._lru.dequeue(index);
                            }
                            return after;
                        };
                        HeapChunkSpace.prototype.unmark = function (index) {
                            var castedIndex = index;
                            var before;
                            var after;
                            do {
                                before = this._chunkMarks.get(castedIndex);
                                if (before > 0) {
                                    after = before - 1;
                                }
                                else {
                                    console.error("WARNING: DOUBLE UNMARK");
                                    after = before;
                                }
                            } while (!this._chunkMarks.compareAndSet(castedIndex, before, after));
                            if (before == 1 && after == 0) {
                                this._lru.enqueue(index);
                            }
                        };
                        HeapChunkSpace.prototype.free = function (chunk) { };
                        HeapChunkSpace.prototype.createAndMark = function (type, world, time, id) {
                            var entry = -1;
                            var hashIndex = org.mwg.utility.HashHelper.tripleHash(type, world, time, id, this._hashEntries);
                            var m = this._hash.get(hashIndex);
                            while (m >= 0) {
                                if (type == this._chunkTypes.get(m) && world == this._chunkWorlds.get(m) && time == this._chunkTimes.get(m) && id == this._chunkIds.get(m)) {
                                    entry = m;
                                    break;
                                }
                                m = this._hashNext.get(m);
                            }
                            if (entry != -1) {
                                var previous = void 0;
                                var after = void 0;
                                do {
                                    previous = this._chunkMarks.get(entry);
                                    if (previous != -1) {
                                        after = previous + 1;
                                    }
                                    else {
                                        after = previous;
                                    }
                                } while (!this._chunkMarks.compareAndSet(entry, previous, after));
                                if (after == (previous + 1)) {
                                    return this._chunkValues.get(entry);
                                }
                            }
                            var currentVictimIndex = -1;
                            while (currentVictimIndex == -1) {
                                var temp_victim = this._lru.dequeueTail();
                                if (temp_victim == -1) {
                                    break;
                                }
                                else {
                                    if (this._chunkMarks.compareAndSet(temp_victim, 0, -1)) {
                                        currentVictimIndex = temp_victim;
                                    }
                                }
                            }
                            if (currentVictimIndex == -1) {
                                throw new Error("mwDB crashed, cache is full, please avoid to much retention of nodes or augment cache capacity! available:" + this.available());
                            }
                            var toInsert = null;
                            switch (type) {
                                case org.mwg.chunk.ChunkType.STATE_CHUNK:
                                    toInsert = new org.mwg.core.chunk.heap.HeapStateChunk(this, currentVictimIndex);
                                    break;
                                case org.mwg.chunk.ChunkType.WORLD_ORDER_CHUNK:
                                    toInsert = new org.mwg.core.chunk.heap.HeapWorldOrderChunk(this, currentVictimIndex);
                                    break;
                                case org.mwg.chunk.ChunkType.TIME_TREE_CHUNK:
                                    toInsert = new org.mwg.core.chunk.heap.HeapTimeTreeChunk(this, currentVictimIndex);
                                    break;
                                case org.mwg.chunk.ChunkType.GEN_CHUNK:
                                    toInsert = new org.mwg.core.chunk.heap.HeapGenChunk(this, id, currentVictimIndex);
                                    break;
                            }
                            if (this._chunkValues.get(currentVictimIndex) != null) {
                                var victimWorld = this._chunkWorlds.get(currentVictimIndex);
                                var victimTime = this._chunkTimes.get(currentVictimIndex);
                                var victimObj = this._chunkIds.get(currentVictimIndex);
                                var victimType = this._chunkTypes.get(currentVictimIndex);
                                var indexVictim = org.mwg.utility.HashHelper.tripleHash(victimType, victimWorld, victimTime, victimObj, this._hashEntries);
                                m = this._hash.get(indexVictim);
                                var last = -1;
                                while (m >= 0) {
                                    if (victimType == this._chunkTypes.get(m) && victimWorld == this._chunkWorlds.get(m) && victimTime == this._chunkTimes.get(m) && victimObj == this._chunkIds.get(m)) {
                                        break;
                                    }
                                    last = m;
                                    m = this._hashNext.get(m);
                                }
                                if (last == -1) {
                                    var previousNext = this._hashNext.get(m);
                                    this._hash.set(indexVictim, previousNext);
                                }
                                else {
                                    if (m == -1) {
                                        this._hashNext.set(last, -1);
                                    }
                                    else {
                                        this._hashNext.set(last, this._hashNext.get(m));
                                    }
                                }
                                this._hashNext.set(m, -1);
                            }
                            this._chunkValues.set(currentVictimIndex, toInsert);
                            this._chunkMarks.set(currentVictimIndex, 1);
                            this._chunkTypes.set(currentVictimIndex, type);
                            this._chunkWorlds.set(currentVictimIndex, world);
                            this._chunkTimes.set(currentVictimIndex, time);
                            this._chunkIds.set(currentVictimIndex, id);
                            this._hashNext.set(currentVictimIndex, this._hash.get(hashIndex));
                            this._hash.set(hashIndex, currentVictimIndex);
                            return toInsert;
                        };
                        HeapChunkSpace.prototype.notifyUpdate = function (index) {
                            if (this._dirtiesStack.enqueue(index)) {
                                this.mark(index);
                            }
                        };
                        HeapChunkSpace.prototype.save = function (callback) {
                            var stream = this._graph.newBuffer();
                            var isFirst = true;
                            while (this._dirtiesStack.size() != 0) {
                                var tail = this._dirtiesStack.dequeueTail();
                                var loopChunk = this._chunkValues.get(tail);
                                if (isFirst) {
                                    isFirst = false;
                                }
                                else {
                                    stream.write(org.mwg.Constants.BUFFER_SEP);
                                }
                                org.mwg.utility.KeyHelper.keyToBuffer(stream, this._chunkTypes.get(tail), this._chunkWorlds.get(tail), this._chunkTimes.get(tail), this._chunkIds.get(tail));
                                stream.write(org.mwg.Constants.BUFFER_SEP);
                                try {
                                    loopChunk.save(stream);
                                    this.unmark(tail);
                                }
                                catch ($ex$) {
                                    if ($ex$ instanceof Error) {
                                        var e = $ex$;
                                        {
                                            console.error(e);
                                        }
                                    }
                                    else {
                                        throw $ex$;
                                    }
                                }
                            }
                            this.graph().storage().put(stream, function (result) {
                                {
                                    stream.free();
                                    if (callback != null) {
                                        callback(result);
                                    }
                                }
                            });
                        };
                        HeapChunkSpace.prototype.clear = function () { };
                        HeapChunkSpace.prototype.freeAll = function () { };
                        HeapChunkSpace.prototype.available = function () {
                            return this._lru.size();
                        };
                        HeapChunkSpace.prototype.printMarked = function () {
                            for (var i = 0; i < this._chunkValues.length(); i++) {
                                if (this._chunkValues.get(i) != null) {
                                    if (this._chunkMarks.get(i) != 0) {
                                        switch (this._chunkTypes.get(i)) {
                                            case org.mwg.chunk.ChunkType.STATE_CHUNK:
                                                console.log("STATE(" + this._chunkWorlds.get(i) + "," + this._chunkTimes.get(i) + "," + this._chunkIds.get(i) + ")->marks->" + this._chunkMarks.get(i));
                                                break;
                                            case org.mwg.chunk.ChunkType.TIME_TREE_CHUNK:
                                                console.log("TIME_TREE(" + this._chunkWorlds.get(i) + "," + this._chunkTimes.get(i) + "," + this._chunkIds.get(i) + ")->marks->" + this._chunkMarks.get(i));
                                                break;
                                            case org.mwg.chunk.ChunkType.WORLD_ORDER_CHUNK:
                                                console.log("WORLD_ORDER(" + this._chunkWorlds.get(i) + "," + this._chunkTimes.get(i) + "," + this._chunkIds.get(i) + ")->marks->" + this._chunkMarks.get(i));
                                                break;
                                            case org.mwg.chunk.ChunkType.GEN_CHUNK:
                                                console.log("GENERATOR(" + this._chunkWorlds.get(i) + "," + this._chunkTimes.get(i) + "," + this._chunkIds.get(i) + ")->marks->" + this._chunkMarks.get(i));
                                                break;
                                        }
                                    }
                                }
                            }
                        };
                        HeapChunkSpace.HASH_LOAD_FACTOR = 4;
                        return HeapChunkSpace;
                    }());
                    heap.HeapChunkSpace = HeapChunkSpace;
                    var HeapFixedStack = (function () {
                        function HeapFixedStack(capacity, fill) {
                            this._capacity = capacity;
                            this._next = new Int32Array(capacity);
                            this._prev = new Int32Array(capacity);
                            this._first = -1;
                            this._last = -1;
                            java.util.Arrays.fill(this._next, 0, capacity, -1);
                            java.util.Arrays.fill(this._prev, 0, capacity, -1);
                            if (fill) {
                                for (var i = 0; i < capacity; i++) {
                                    var l = this._last;
                                    this._prev[i] = l;
                                    this._last = i;
                                    if (this._first == -1) {
                                        this._first = i;
                                    }
                                    else {
                                        this._next[l] = i;
                                    }
                                }
                                this._count = capacity;
                            }
                            else {
                                this._count = 0;
                            }
                        }
                        HeapFixedStack.prototype.enqueue = function (index) {
                            if (this._count >= this._capacity) {
                                return false;
                            }
                            var castedIndex = index;
                            if (this._first == castedIndex || this._last == castedIndex) {
                                return false;
                            }
                            if (this._prev[castedIndex] != -1 || this._next[castedIndex] != -1) {
                                return false;
                            }
                            var l = this._last;
                            this._prev[castedIndex] = l;
                            this._last = castedIndex;
                            if (this._first == -1) {
                                this._first = castedIndex;
                            }
                            else {
                                this._next[l] = castedIndex;
                            }
                            this._count++;
                            return true;
                        };
                        HeapFixedStack.prototype.dequeueTail = function () {
                            var f = this._first;
                            if (f == -1) {
                                return -1;
                            }
                            var n = this._next[f];
                            this._next[f] = -1;
                            this._prev[f] = -1;
                            this._first = n;
                            if (n == -1) {
                                this._last = -1;
                            }
                            else {
                                this._prev[n] = -1;
                            }
                            this._count--;
                            return f;
                        };
                        HeapFixedStack.prototype.dequeue = function (index) {
                            var castedIndex = index;
                            var p = this._prev[castedIndex];
                            var n = this._next[castedIndex];
                            if (p == -1 && n == -1) {
                                return false;
                            }
                            if (p == -1) {
                                var f = this._first;
                                if (f == -1) {
                                    return false;
                                }
                                var n2 = this._next[f];
                                this._next[f] = -1;
                                this._prev[f] = -1;
                                this._first = n2;
                                if (n2 == -1) {
                                    this._last = -1;
                                }
                                else {
                                    this._prev[n2] = -1;
                                }
                                --this._count;
                            }
                            else if (n == -1) {
                                var l = this._last;
                                if (l == -1) {
                                    return false;
                                }
                                var p2 = this._prev[l];
                                this._prev[l] = -1;
                                this._next[l] = -1;
                                this._last = p2;
                                if (p2 == -1) {
                                    this._first = -1;
                                }
                                else {
                                    this._next[p2] = -1;
                                }
                                --this._count;
                            }
                            else {
                                this._next[p] = n;
                                this._prev[n] = p;
                                this._prev[castedIndex] = -1;
                                this._next[castedIndex] = -1;
                                this._count--;
                            }
                            return true;
                        };
                        HeapFixedStack.prototype.free = function () { };
                        HeapFixedStack.prototype.size = function () {
                            return this._count;
                        };
                        return HeapFixedStack;
                    }());
                    heap.HeapFixedStack = HeapFixedStack;
                    var HeapGenChunk = (function () {
                        function HeapGenChunk(p_space, p_id, p_index) {
                            this._index = p_index;
                            this._space = p_space;
                            this._prefix = Long.fromNumber(p_id).shiftLeft((org.mwg.Constants.LONG_SIZE - org.mwg.Constants.PREFIX_SIZE));
                            this._seed = -1;
                        }
                        HeapGenChunk.prototype.save = function (buffer) {
                            org.mwg.utility.Base64.encodeLongToBuffer(this._seed, buffer);
                            this._dirty = false;
                        };
                        HeapGenChunk.prototype.load = function (buffer) {
                            if (buffer == null || buffer.length() == 0) {
                                return;
                            }
                            var loaded = org.mwg.utility.Base64.decodeToLongWithBounds(buffer, 0, buffer.length());
                            var previousSeed = this._seed;
                            this._seed = loaded;
                            if (previousSeed != -1 && previousSeed != this._seed) {
                                if (this._space != null && !this._dirty) {
                                    this._dirty = true;
                                    this._space.notifyUpdate(this._index);
                                }
                            }
                        };
                        HeapGenChunk.prototype.newKey = function () {
                            if (this._seed == org.mwg.Constants.KEY_PREFIX_MASK) {
                                throw new Error("Object Index could not be created because it exceeded the capacity of the current prefix. Ask for a new prefix.");
                            }
                            if (this._seed == -1) {
                                this._seed = 0;
                            }
                            this._seed++;
                            var nextIndex = this._seed;
                            if (this._space) {
                                this._space.notifyUpdate(this._index);
                            }
                            var objectKey = this._prefix.add(this._seed).toNumber();
                            if (objectKey >= org.mwg.Constants.NULL_LONG) {
                                throw new Error("Object Index exceeds the maximum JavaScript number capacity. (2^" + org.mwg.Constants.LONG_SIZE + ")");
                            }
                            return objectKey;
                        };
                        HeapGenChunk.prototype.index = function () {
                            return this._index;
                        };
                        HeapGenChunk.prototype.world = function () {
                            return this._space.worldByIndex(this._index);
                        };
                        HeapGenChunk.prototype.time = function () {
                            return this._space.timeByIndex(this._index);
                        };
                        HeapGenChunk.prototype.id = function () {
                            return this._space.idByIndex(this._index);
                        };
                        HeapGenChunk.prototype.chunkType = function () {
                            return org.mwg.chunk.ChunkType.GEN_CHUNK;
                        };
                        return HeapGenChunk;
                    }());
                    heap.HeapGenChunk = HeapGenChunk;
                    var HeapLongLongArrayMap = (function () {
                        function HeapLongLongArrayMap(p_listener) {
                            this.mapSize = 0;
                            this.capacity = 0;
                            this.keys = null;
                            this.values = null;
                            this.nexts = null;
                            this.hashs = null;
                            this.parent = p_listener;
                        }
                        HeapLongLongArrayMap.prototype.key = function (i) {
                            return this.keys[i];
                        };
                        HeapLongLongArrayMap.prototype.setKey = function (i, newValue) {
                            this.keys[i] = newValue;
                        };
                        HeapLongLongArrayMap.prototype.value = function (i) {
                            return this.values[i];
                        };
                        HeapLongLongArrayMap.prototype.setValue = function (i, newValue) {
                            this.values[i] = newValue;
                        };
                        HeapLongLongArrayMap.prototype.next = function (i) {
                            return this.nexts[i];
                        };
                        HeapLongLongArrayMap.prototype.setNext = function (i, newValue) {
                            this.nexts[i] = newValue;
                        };
                        HeapLongLongArrayMap.prototype.hash = function (i) {
                            return this.hashs[i];
                        };
                        HeapLongLongArrayMap.prototype.setHash = function (i, newValue) {
                            this.hashs[i] = newValue;
                        };
                        HeapLongLongArrayMap.prototype.reallocate = function (newCapacity) {
                            if (newCapacity > this.capacity) {
                                var new_keys = new Float64Array(newCapacity);
                                if (this.keys != null) {
                                    java.lang.System.arraycopy(this.keys, 0, new_keys, 0, this.capacity);
                                }
                                this.keys = new_keys;
                                var new_values = new Float64Array(newCapacity);
                                if (this.values != null) {
                                    java.lang.System.arraycopy(this.values, 0, new_values, 0, this.capacity);
                                }
                                this.values = new_values;
                                var new_nexts = new Int32Array(newCapacity);
                                var new_hashes = new Int32Array(newCapacity * 2);
                                java.util.Arrays.fill(new_nexts, 0, newCapacity, -1);
                                java.util.Arrays.fill(new_hashes, 0, newCapacity * 2, -1);
                                this.hashs = new_hashes;
                                this.nexts = new_nexts;
                                for (var i = 0; i < this.mapSize; i++) {
                                    var new_key_hash = org.mwg.utility.HashHelper.longHash(this.key(i), newCapacity * 2);
                                    this.setNext(i, this.hash(new_key_hash));
                                    this.setHash(new_key_hash, i);
                                }
                                this.capacity = newCapacity;
                            }
                        };
                        HeapLongLongArrayMap.prototype.cloneFor = function (newParent) {
                            var cloned = new org.mwg.core.chunk.heap.HeapLongLongArrayMap(newParent);
                            cloned.mapSize = this.mapSize;
                            cloned.capacity = this.capacity;
                            if (this.keys != null) {
                                var cloned_keys = new Float64Array(this.capacity);
                                java.lang.System.arraycopy(this.keys, 0, cloned_keys, 0, this.capacity);
                                cloned.keys = cloned_keys;
                            }
                            if (this.values != null) {
                                var cloned_values = new Float64Array(this.capacity);
                                java.lang.System.arraycopy(this.values, 0, cloned_values, 0, this.capacity);
                                cloned.values = cloned_values;
                            }
                            if (this.nexts != null) {
                                var cloned_nexts = new Int32Array(this.capacity);
                                java.lang.System.arraycopy(this.nexts, 0, cloned_nexts, 0, this.capacity);
                                cloned.nexts = cloned_nexts;
                            }
                            if (this.hashs != null) {
                                var cloned_hashs = new Int32Array(this.capacity * 2);
                                java.lang.System.arraycopy(this.hashs, 0, cloned_hashs, 0, this.capacity * 2);
                                cloned.hashs = cloned_hashs;
                            }
                            return cloned;
                        };
                        HeapLongLongArrayMap.prototype.get = function (requestKey) {
                            var result = new Float64Array(0);
                            {
                                if (this.keys != null) {
                                    var hashIndex = org.mwg.utility.HashHelper.longHash(requestKey, this.capacity * 2);
                                    var resultCapacity = 0;
                                    var resultIndex = 0;
                                    var m = this.hash(hashIndex);
                                    while (m >= 0) {
                                        if (requestKey == this.key(m)) {
                                            if (resultIndex == resultCapacity) {
                                                var newCapacity = void 0;
                                                if (resultCapacity == 0) {
                                                    newCapacity = 1;
                                                }
                                                else {
                                                    newCapacity = resultCapacity * 2;
                                                }
                                                var tempResult = new Float64Array(newCapacity);
                                                java.lang.System.arraycopy(result, 0, tempResult, 0, result.length);
                                                result = tempResult;
                                                resultCapacity = newCapacity;
                                            }
                                            result[resultIndex] = this.value(m);
                                            resultIndex++;
                                        }
                                        m = this.next(m);
                                    }
                                    if (resultIndex != resultCapacity) {
                                        var shrinkedResult = new Float64Array(resultIndex);
                                        java.lang.System.arraycopy(result, 0, shrinkedResult, 0, resultIndex);
                                        result = shrinkedResult;
                                    }
                                }
                            }
                            return result;
                        };
                        HeapLongLongArrayMap.prototype.contains = function (requestKey, requestValue) {
                            var result = false;
                            {
                                if (this.keys != null) {
                                    var hashIndex = org.mwg.utility.HashHelper.longHash(requestKey, this.capacity * 2);
                                    var m = this.hash(hashIndex);
                                    while (m >= 0 && !result) {
                                        if (requestKey == this.key(m) && requestValue == this.value(m)) {
                                            result = true;
                                        }
                                        m = this.next(m);
                                    }
                                }
                            }
                            return result;
                        };
                        HeapLongLongArrayMap.prototype.each = function (callback) {
                            {
                                this.unsafe_each(callback);
                            }
                        };
                        HeapLongLongArrayMap.prototype.unsafe_each = function (callback) {
                            for (var i = 0; i < this.mapSize; i++) {
                                callback(this.key(i), this.value(i));
                            }
                        };
                        HeapLongLongArrayMap.prototype.size = function () {
                            var result;
                            {
                                result = this.mapSize;
                            }
                            return result;
                        };
                        HeapLongLongArrayMap.prototype.remove = function (requestKey, requestValue) {
                            {
                                if (this.keys != null && this.mapSize != 0) {
                                    var hashCapacity = this.capacity * 2;
                                    var hashIndex = org.mwg.utility.HashHelper.longHash(requestKey, hashCapacity);
                                    var m = this.hash(hashIndex);
                                    var found = -1;
                                    while (m >= 0) {
                                        if (requestKey == this.key(m) && requestValue == this.value(m)) {
                                            found = m;
                                            break;
                                        }
                                        m = this.next(m);
                                    }
                                    if (found != -1) {
                                        var toRemoveHash = org.mwg.utility.HashHelper.longHash(requestKey, hashCapacity);
                                        m = this.hash(toRemoveHash);
                                        if (m == found) {
                                            this.setHash(toRemoveHash, this.next(m));
                                        }
                                        else {
                                            while (m != -1) {
                                                var next_of_m = this.next(m);
                                                if (next_of_m == found) {
                                                    this.setNext(m, this.next(next_of_m));
                                                    break;
                                                }
                                                m = next_of_m;
                                            }
                                        }
                                        var lastIndex = this.mapSize - 1;
                                        if (lastIndex == found) {
                                            this.mapSize--;
                                        }
                                        else {
                                            var lastKey = this.key(lastIndex);
                                            this.setKey(found, lastKey);
                                            this.setValue(found, this.value(lastIndex));
                                            this.setNext(found, this.next(lastIndex));
                                            var victimHash = org.mwg.utility.HashHelper.longHash(lastKey, hashCapacity);
                                            m = this.hash(victimHash);
                                            if (m == lastIndex) {
                                                this.setHash(victimHash, found);
                                            }
                                            else {
                                                while (m != -1) {
                                                    var next_of_m = this.next(m);
                                                    if (next_of_m == lastIndex) {
                                                        this.setNext(m, found);
                                                        break;
                                                    }
                                                    m = next_of_m;
                                                }
                                            }
                                            this.mapSize--;
                                        }
                                        this.parent.declareDirty();
                                    }
                                }
                            }
                        };
                        HeapLongLongArrayMap.prototype.put = function (insertKey, insertValue) {
                            {
                                if (this.keys == null) {
                                    this.reallocate(org.mwg.Constants.MAP_INITIAL_CAPACITY);
                                    this.setKey(0, insertKey);
                                    this.setValue(0, insertValue);
                                    this.setHash(org.mwg.utility.HashHelper.longHash(insertKey, this.capacity * 2), 0);
                                    this.setNext(0, -1);
                                    this.mapSize++;
                                    this.parent.declareDirty();
                                }
                                else {
                                    var hashCapacity = this.capacity * 2;
                                    var insertKeyHash = org.mwg.utility.HashHelper.longHash(insertKey, hashCapacity);
                                    var currentHash = this.hash(insertKeyHash);
                                    var m = currentHash;
                                    var found = -1;
                                    while (m >= 0) {
                                        if (insertKey == this.key(m) && insertValue == this.value(m)) {
                                            found = m;
                                            break;
                                        }
                                        m = this.next(m);
                                    }
                                    if (found == -1) {
                                        var lastIndex = this.mapSize;
                                        if (lastIndex == this.capacity) {
                                            this.reallocate(this.capacity * 2);
                                        }
                                        this.setKey(lastIndex, insertKey);
                                        this.setValue(lastIndex, insertValue);
                                        this.setHash(org.mwg.utility.HashHelper.longHash(insertKey, this.capacity * 2), lastIndex);
                                        this.setNext(lastIndex, currentHash);
                                        this.mapSize++;
                                        this.parent.declareDirty();
                                    }
                                }
                            }
                        };
                        return HeapLongLongArrayMap;
                    }());
                    heap.HeapLongLongArrayMap = HeapLongLongArrayMap;
                    var HeapLongLongMap = (function () {
                        function HeapLongLongMap(p_listener) {
                            this.mapSize = 0;
                            this.capacity = 0;
                            this.keys = null;
                            this.values = null;
                            this.nexts = null;
                            this.hashs = null;
                            this.parent = p_listener;
                        }
                        HeapLongLongMap.prototype.key = function (i) {
                            return this.keys[i];
                        };
                        HeapLongLongMap.prototype.setKey = function (i, newValue) {
                            this.keys[i] = newValue;
                        };
                        HeapLongLongMap.prototype.value = function (i) {
                            return this.values[i];
                        };
                        HeapLongLongMap.prototype.setValue = function (i, newValue) {
                            this.values[i] = newValue;
                        };
                        HeapLongLongMap.prototype.next = function (i) {
                            return this.nexts[i];
                        };
                        HeapLongLongMap.prototype.setNext = function (i, newValue) {
                            this.nexts[i] = newValue;
                        };
                        HeapLongLongMap.prototype.hash = function (i) {
                            return this.hashs[i];
                        };
                        HeapLongLongMap.prototype.setHash = function (i, newValue) {
                            this.hashs[i] = newValue;
                        };
                        HeapLongLongMap.prototype.reallocate = function (newCapacity) {
                            if (newCapacity > this.capacity) {
                                var new_keys = new Float64Array(newCapacity);
                                if (this.keys != null) {
                                    java.lang.System.arraycopy(this.keys, 0, new_keys, 0, this.capacity);
                                }
                                this.keys = new_keys;
                                var new_values = new Float64Array(newCapacity);
                                if (this.values != null) {
                                    java.lang.System.arraycopy(this.values, 0, new_values, 0, this.capacity);
                                }
                                this.values = new_values;
                                var new_nexts = new Int32Array(newCapacity);
                                var new_hashes = new Int32Array(newCapacity * 2);
                                java.util.Arrays.fill(new_nexts, 0, newCapacity, -1);
                                java.util.Arrays.fill(new_hashes, 0, newCapacity * 2, -1);
                                this.hashs = new_hashes;
                                this.nexts = new_nexts;
                                for (var i = 0; i < this.mapSize; i++) {
                                    var new_key_hash = org.mwg.utility.HashHelper.longHash(this.key(i), newCapacity * 2);
                                    this.setNext(i, this.hash(new_key_hash));
                                    this.setHash(new_key_hash, i);
                                }
                                this.capacity = newCapacity;
                            }
                        };
                        HeapLongLongMap.prototype.cloneFor = function (newParent) {
                            var cloned = new org.mwg.core.chunk.heap.HeapLongLongMap(newParent);
                            cloned.mapSize = this.mapSize;
                            cloned.capacity = this.capacity;
                            if (this.keys != null) {
                                var cloned_keys = new Float64Array(this.capacity);
                                java.lang.System.arraycopy(this.keys, 0, cloned_keys, 0, this.capacity);
                                cloned.keys = cloned_keys;
                            }
                            if (this.values != null) {
                                var cloned_values = new Float64Array(this.capacity);
                                java.lang.System.arraycopy(this.values, 0, cloned_values, 0, this.capacity);
                                cloned.values = cloned_values;
                            }
                            if (this.nexts != null) {
                                var cloned_nexts = new Int32Array(this.capacity);
                                java.lang.System.arraycopy(this.nexts, 0, cloned_nexts, 0, this.capacity);
                                cloned.nexts = cloned_nexts;
                            }
                            if (this.hashs != null) {
                                var cloned_hashs = new Int32Array(this.capacity * 2);
                                java.lang.System.arraycopy(this.hashs, 0, cloned_hashs, 0, this.capacity * 2);
                                cloned.hashs = cloned_hashs;
                            }
                            return cloned;
                        };
                        HeapLongLongMap.prototype.get = function (requestKey) {
                            var result = org.mwg.Constants.NULL_LONG;
                            {
                                if (this.keys != null) {
                                    var hashIndex = org.mwg.utility.HashHelper.longHash(requestKey, this.capacity * 2);
                                    var m = this.hash(hashIndex);
                                    while (m >= 0) {
                                        if (requestKey == this.key(m)) {
                                            result = this.value(m);
                                            break;
                                        }
                                        m = this.next(m);
                                    }
                                }
                            }
                            return result;
                        };
                        HeapLongLongMap.prototype.each = function (callback) {
                            {
                                this.unsafe_each(callback);
                            }
                        };
                        HeapLongLongMap.prototype.unsafe_each = function (callback) {
                            for (var i = 0; i < this.mapSize; i++) {
                                callback(this.key(i), this.value(i));
                            }
                        };
                        HeapLongLongMap.prototype.size = function () {
                            var result;
                            {
                                result = this.mapSize;
                            }
                            return result;
                        };
                        HeapLongLongMap.prototype.remove = function (requestKey) {
                            {
                                if (this.keys != null && this.mapSize != 0) {
                                    var hashCapacity = this.capacity * 2;
                                    var hashIndex = org.mwg.utility.HashHelper.longHash(requestKey, hashCapacity);
                                    var m = this.hash(hashIndex);
                                    var found = -1;
                                    while (m >= 0) {
                                        if (requestKey == this.key(m)) {
                                            found = m;
                                            break;
                                        }
                                        m = this.next(m);
                                    }
                                    if (found != -1) {
                                        var toRemoveHash = org.mwg.utility.HashHelper.longHash(requestKey, hashCapacity);
                                        m = this.hash(toRemoveHash);
                                        if (m == found) {
                                            this.setHash(toRemoveHash, this.next(m));
                                        }
                                        else {
                                            while (m != -1) {
                                                var next_of_m = this.next(m);
                                                if (next_of_m == found) {
                                                    this.setNext(m, this.next(next_of_m));
                                                    break;
                                                }
                                                m = next_of_m;
                                            }
                                        }
                                        var lastIndex = this.mapSize - 1;
                                        if (lastIndex == found) {
                                            this.mapSize--;
                                        }
                                        else {
                                            var lastKey = this.key(lastIndex);
                                            this.setKey(found, lastKey);
                                            this.setValue(found, this.value(lastIndex));
                                            this.setNext(found, this.next(lastIndex));
                                            var victimHash = org.mwg.utility.HashHelper.longHash(lastKey, hashCapacity);
                                            m = this.hash(victimHash);
                                            if (m == lastIndex) {
                                                this.setHash(victimHash, found);
                                            }
                                            else {
                                                while (m != -1) {
                                                    var next_of_m = this.next(m);
                                                    if (next_of_m == lastIndex) {
                                                        this.setNext(m, found);
                                                        break;
                                                    }
                                                    m = next_of_m;
                                                }
                                            }
                                            this.mapSize--;
                                        }
                                        this.parent.declareDirty();
                                    }
                                }
                            }
                        };
                        HeapLongLongMap.prototype.put = function (insertKey, insertValue) {
                            {
                                if (this.keys == null) {
                                    this.reallocate(org.mwg.Constants.MAP_INITIAL_CAPACITY);
                                    this.setKey(0, insertKey);
                                    this.setValue(0, insertValue);
                                    this.setHash(org.mwg.utility.HashHelper.longHash(insertKey, this.capacity * 2), 0);
                                    this.setNext(0, -1);
                                    this.mapSize++;
                                }
                                else {
                                    var hashCapacity = this.capacity * 2;
                                    var insertKeyHash = org.mwg.utility.HashHelper.longHash(insertKey, hashCapacity);
                                    var currentHash = this.hash(insertKeyHash);
                                    var m = currentHash;
                                    var found = -1;
                                    while (m >= 0) {
                                        if (insertKey == this.key(m)) {
                                            found = m;
                                            break;
                                        }
                                        m = this.next(m);
                                    }
                                    if (found == -1) {
                                        var lastIndex = this.mapSize;
                                        if (lastIndex == this.capacity) {
                                            this.reallocate(this.capacity * 2);
                                        }
                                        this.setKey(lastIndex, insertKey);
                                        this.setValue(lastIndex, insertValue);
                                        this.setHash(org.mwg.utility.HashHelper.longHash(insertKey, this.capacity * 2), lastIndex);
                                        this.setNext(lastIndex, currentHash);
                                        this.mapSize++;
                                        this.parent.declareDirty();
                                    }
                                    else {
                                        if (this.value(found) != insertValue) {
                                            this.setValue(found, insertValue);
                                            this.parent.declareDirty();
                                        }
                                    }
                                }
                            }
                        };
                        return HeapLongLongMap;
                    }());
                    heap.HeapLongLongMap = HeapLongLongMap;
                    var HeapRelationship = (function () {
                        function HeapRelationship(p_listener, origin) {
                            this.aligned = true;
                            this.parent = p_listener;
                            if (origin != null) {
                                this.aligned = false;
                                this._back = origin._back;
                                this._size = origin._size;
                            }
                            else {
                                this._back = null;
                                this._size = 0;
                            }
                        }
                        HeapRelationship.prototype.allocate = function (_capacity) {
                            var new_back = new Float64Array(_capacity);
                            if (this._back != null) {
                                java.lang.System.arraycopy(this._back, 0, new_back, 0, this._back.length);
                            }
                            this._back = new_back;
                        };
                        HeapRelationship.prototype.size = function () {
                            return this._size;
                        };
                        HeapRelationship.prototype.get = function (index) {
                            var result;
                            {
                                result = this._back[index];
                            }
                            return result;
                        };
                        HeapRelationship.prototype.set = function (index, value) {
                            {
                                this._back[index] = value;
                            }
                        };
                        HeapRelationship.prototype.unsafe_get = function (index) {
                            return this._back[index];
                        };
                        HeapRelationship.prototype.add = function (newValue) {
                            {
                                if (!this.aligned) {
                                    var temp_back = new Float64Array(this._back.length);
                                    java.lang.System.arraycopy(this._back, 0, temp_back, 0, this._back.length);
                                    this._back = temp_back;
                                    this.aligned = true;
                                }
                                if (this._back == null) {
                                    this._back = new Float64Array(org.mwg.Constants.MAP_INITIAL_CAPACITY);
                                    this._back[0] = newValue;
                                    this._size = 1;
                                }
                                else if (this._size == this._back.length) {
                                    var ex_back = new Float64Array(this._back.length * 2);
                                    java.lang.System.arraycopy(this._back, 0, ex_back, 0, this._size);
                                    this._back = ex_back;
                                    this._back[this._size] = newValue;
                                    this._size++;
                                }
                                else {
                                    this._back[this._size] = newValue;
                                    this._size++;
                                }
                                this.parent.declareDirty();
                            }
                            return this;
                        };
                        HeapRelationship.prototype.remove = function (oldValue) {
                            {
                                if (!this.aligned) {
                                    var temp_back = new Float64Array(this._back.length);
                                    java.lang.System.arraycopy(this._back, 0, temp_back, 0, this._back.length);
                                    this._back = temp_back;
                                    this.aligned = true;
                                }
                                var indexToRemove = -1;
                                for (var i = 0; i < this._size; i++) {
                                    if (this._back[i] == oldValue) {
                                        indexToRemove = i;
                                        break;
                                    }
                                }
                                if (indexToRemove != -1) {
                                    if ((this._size - 1) == 0) {
                                        this._back = null;
                                        this._size = 0;
                                    }
                                    else {
                                        var red_back = new Float64Array(this._size - 1);
                                        java.lang.System.arraycopy(this._back, 0, red_back, 0, indexToRemove);
                                        java.lang.System.arraycopy(this._back, indexToRemove + 1, red_back, indexToRemove, this._size - indexToRemove - 1);
                                        this._back = red_back;
                                        this._size--;
                                    }
                                }
                            }
                            return this;
                        };
                        HeapRelationship.prototype.clear = function () {
                            {
                                this._size = 0;
                                if (!this.aligned) {
                                    this._back = null;
                                    this.aligned = true;
                                }
                                else {
                                    if (this._back != null) {
                                        java.util.Arrays.fill(this._back, 0, this._back.length, -1);
                                    }
                                }
                            }
                            return this;
                        };
                        HeapRelationship.prototype.toString = function () {
                            var buffer = new java.lang.StringBuilder();
                            buffer.append("[");
                            for (var i = 0; i < this._size; i++) {
                                if (i != 0) {
                                    buffer.append(",");
                                }
                                buffer.append(this._back[i]);
                            }
                            buffer.append("]");
                            return buffer.toString();
                        };
                        return HeapRelationship;
                    }());
                    heap.HeapRelationship = HeapRelationship;
                    var HeapStateChunk = (function () {
                        function HeapStateChunk(p_space, p_index) {
                            this._space = p_space;
                            this._index = p_index;
                            this._next = null;
                            this._hash = null;
                            this._type = null;
                            this._size = 0;
                            this._capacity = 0;
                            this._dirty = false;
                        }
                        HeapStateChunk.prototype.world = function () {
                            return this._space.worldByIndex(this._index);
                        };
                        HeapStateChunk.prototype.time = function () {
                            return this._space.timeByIndex(this._index);
                        };
                        HeapStateChunk.prototype.id = function () {
                            return this._space.idByIndex(this._index);
                        };
                        HeapStateChunk.prototype.chunkType = function () {
                            return org.mwg.chunk.ChunkType.STATE_CHUNK;
                        };
                        HeapStateChunk.prototype.index = function () {
                            return this._index;
                        };
                        HeapStateChunk.prototype.get = function (p_key) {
                            return this.internal_get(p_key);
                        };
                        HeapStateChunk.prototype.internal_find = function (p_key) {
                            if (this._size == 0) {
                                return -1;
                            }
                            else if (this._hash == null) {
                                for (var i = 0; i < this._size; i++) {
                                    if (this._k[i] == p_key) {
                                        return i;
                                    }
                                }
                                return -1;
                            }
                            else {
                                var hashIndex = org.mwg.utility.HashHelper.longHash(p_key, this._capacity * 2);
                                var m = this._hash[hashIndex];
                                while (m >= 0) {
                                    if (p_key == this._k[m]) {
                                        return m;
                                    }
                                    else {
                                        m = this._next[m];
                                    }
                                }
                                return -1;
                            }
                        };
                        HeapStateChunk.prototype.internal_get = function (p_key) {
                            if (this._size == 0) {
                                return null;
                            }
                            var found = this.internal_find(p_key);
                            var result;
                            if (found != -1) {
                                result = this._v[found];
                                if (result != null) {
                                    switch (this._type[found]) {
                                        case org.mwg.Type.DOUBLE_ARRAY:
                                            var castedResultD = result;
                                            var copyD = new Float64Array(castedResultD.length);
                                            java.lang.System.arraycopy(castedResultD, 0, copyD, 0, castedResultD.length);
                                            return copyD;
                                        case org.mwg.Type.LONG_ARRAY:
                                            var castedResultL = result;
                                            var copyL = new Float64Array(castedResultL.length);
                                            java.lang.System.arraycopy(castedResultL, 0, copyL, 0, castedResultL.length);
                                            return copyL;
                                        case org.mwg.Type.INT_ARRAY:
                                            var castedResultI = result;
                                            var copyI = new Int32Array(castedResultI.length);
                                            java.lang.System.arraycopy(castedResultI, 0, copyI, 0, castedResultI.length);
                                            return copyI;
                                        default:
                                            return result;
                                    }
                                }
                            }
                            return null;
                        };
                        HeapStateChunk.prototype.set = function (p_elementIndex, p_elemType, p_unsafe_elem) {
                            if (p_unsafe_elem != null) {
                                if (p_elemType == org.mwg.Type.STRING) {
                                    if (!(typeof p_unsafe_elem === 'string')) {
                                        throw new Error("mwDB usage error, set method called with type " + org.mwg.Type.typeName(p_elemType) + " while param object is " + p_unsafe_elem);
                                    }
                                }
                                if (p_elemType == org.mwg.Type.BOOL) {
                                    if (!(typeof p_unsafe_elem === 'boolean')) {
                                        throw new Error("mwDB usage error, set method called with type " + org.mwg.Type.typeName(p_elemType) + " while param object is " + p_unsafe_elem);
                                    }
                                }
                                if (p_elemType == org.mwg.Type.DOUBLE || p_elemType == org.mwg.Type.LONG || p_elemType == org.mwg.Type.INT) {
                                    if (!(typeof p_unsafe_elem === 'number')) {
                                        throw new Error("mwDB usage error, set method called with type " + org.mwg.Type.typeName(p_elemType) + " while param object is " + p_unsafe_elem);
                                    }
                                }
                                if (p_elemType == org.mwg.Type.DOUBLE_ARRAY) {
                                    if (!(p_unsafe_elem instanceof Float64Array)) {
                                        throw new Error("mwDB usage error, set method called with type " + org.mwg.Type.typeName(p_elemType) + " while param object is " + p_unsafe_elem);
                                    }
                                }
                                if (p_elemType == org.mwg.Type.LONG_ARRAY) {
                                    if (!(p_unsafe_elem instanceof Float64Array)) {
                                        throw new Error("mwDB usage error, set method called with type " + org.mwg.Type.typeName(p_elemType) + " while param object is " + p_unsafe_elem);
                                    }
                                }
                                if (p_elemType == org.mwg.Type.INT_ARRAY) {
                                    if (!(p_unsafe_elem instanceof Int32Array)) {
                                        throw new Error("mwDB usage error, set method called with type " + org.mwg.Type.typeName(p_elemType) + " while param object is " + p_unsafe_elem);
                                    }
                                }
                                if (p_elemType == org.mwg.Type.STRING_TO_LONG_MAP) {
                                    if (!(typeof p_unsafe_elem === 'object')) {
                                        throw new Error("mwDB usage error, set method called with type " + org.mwg.Type.typeName(p_elemType) + " while param object is " + p_unsafe_elem);
                                    }
                                }
                                if (p_elemType == org.mwg.Type.LONG_TO_LONG_MAP) {
                                    if (!(typeof p_unsafe_elem === 'boolean')) {
                                        throw new Error("mwDB usage error, set method called with type " + org.mwg.Type.typeName(p_elemType) + " while param object is " + p_unsafe_elem);
                                    }
                                }
                                if (p_elemType == org.mwg.Type.LONG_TO_LONG_ARRAY_MAP) {
                                    if (!(typeof p_unsafe_elem === 'boolean')) {
                                        throw new Error("mwDB usage error, set method called with type " + org.mwg.Type.typeName(p_elemType) + " while param object is " + p_unsafe_elem);
                                    }
                                }
                            }
                            this.internal_set(p_elementIndex, p_elemType, p_unsafe_elem, true, false);
                        };
                        HeapStateChunk.prototype.setFromKey = function (key, p_elemType, p_unsafe_elem) {
                            this.internal_set(this._space.graph().resolver().stringToHash(key, true), p_elemType, p_unsafe_elem, true, false);
                        };
                        HeapStateChunk.prototype.getFromKey = function (key) {
                            return this.internal_get(this._space.graph().resolver().stringToHash(key, false));
                        };
                        HeapStateChunk.prototype.getFromKeyWithDefault = function (key, defaultValue) {
                            var result = this.getFromKey(key);
                            if (result == null) {
                                return defaultValue;
                            }
                            else {
                                return result;
                            }
                        };
                        HeapStateChunk.prototype.getWithDefault = function (key, defaultValue) {
                            var result = this.get(key);
                            if (result == null) {
                                return defaultValue;
                            }
                            else {
                                return result;
                            }
                        };
                        HeapStateChunk.prototype.getType = function (p_key) {
                            if (this._size == 0) {
                                return -1;
                            }
                            if (this._hash == null) {
                                for (var i = 0; i < this._capacity; i++) {
                                    if (this._k[i] == p_key) {
                                        return this._type[i];
                                    }
                                }
                            }
                            else {
                                var hashIndex = org.mwg.utility.HashHelper.longHash(p_key, this._capacity * 2);
                                var m = this._hash[hashIndex];
                                while (m >= 0) {
                                    if (p_key == this._k[m]) {
                                        return this._type[m];
                                    }
                                    else {
                                        m = this._next[m];
                                    }
                                }
                            }
                            return -1;
                        };
                        HeapStateChunk.prototype.getTypeFromKey = function (key) {
                            return this.getType(this._space.graph().resolver().stringToHash(key, false));
                        };
                        HeapStateChunk.prototype.getOrCreate = function (p_key, p_type) {
                            var found = this.internal_find(p_key);
                            if (found != -1) {
                                if (this._type[found] == p_type) {
                                    return this._v[found];
                                }
                            }
                            var toSet = null;
                            switch (p_type) {
                                case org.mwg.Type.RELATION:
                                    toSet = new org.mwg.core.chunk.heap.HeapRelationship(this, null);
                                    break;
                                case org.mwg.Type.STRING_TO_LONG_MAP:
                                    toSet = new org.mwg.core.chunk.heap.HeapStringLongMap(this);
                                    break;
                                case org.mwg.Type.LONG_TO_LONG_MAP:
                                    toSet = new org.mwg.core.chunk.heap.HeapLongLongMap(this);
                                    break;
                                case org.mwg.Type.LONG_TO_LONG_ARRAY_MAP:
                                    toSet = new org.mwg.core.chunk.heap.HeapLongLongArrayMap(this);
                                    break;
                            }
                            this.internal_set(p_key, p_type, toSet, true, false);
                            return toSet;
                        };
                        HeapStateChunk.prototype.getOrCreateFromKey = function (key, elemType) {
                            return this.getOrCreate(this._space.graph().resolver().stringToHash(key, true), elemType);
                        };
                        HeapStateChunk.prototype.declareDirty = function () {
                            if (this._space != null && !this._dirty) {
                                this._dirty = true;
                                this._space.notifyUpdate(this._index);
                            }
                        };
                        HeapStateChunk.prototype.save = function (buffer) {
                            org.mwg.utility.Base64.encodeIntToBuffer(this._size, buffer);
                            for (var i = 0; i < this._size; i++) {
                                if (this._v[i] != null) {
                                    var loopValue = this._v[i];
                                    if (loopValue != null) {
                                        buffer.write(org.mwg.core.CoreConstants.CHUNK_SEP);
                                        org.mwg.utility.Base64.encodeLongToBuffer(this._k[i], buffer);
                                        buffer.write(org.mwg.core.CoreConstants.CHUNK_SUB_SEP);
                                        org.mwg.utility.Base64.encodeIntToBuffer(this._type[i], buffer);
                                        buffer.write(org.mwg.core.CoreConstants.CHUNK_SUB_SEP);
                                        switch (this._type[i]) {
                                            case org.mwg.Type.STRING:
                                                org.mwg.utility.Base64.encodeStringToBuffer(loopValue, buffer);
                                                break;
                                            case org.mwg.Type.BOOL:
                                                if (this._v[i]) {
                                                    buffer.write(org.mwg.core.CoreConstants.BOOL_TRUE);
                                                }
                                                else {
                                                    buffer.write(org.mwg.core.CoreConstants.BOOL_FALSE);
                                                }
                                                break;
                                            case org.mwg.Type.LONG:
                                                org.mwg.utility.Base64.encodeLongToBuffer(loopValue, buffer);
                                                break;
                                            case org.mwg.Type.DOUBLE:
                                                org.mwg.utility.Base64.encodeDoubleToBuffer(loopValue, buffer);
                                                break;
                                            case org.mwg.Type.INT:
                                                org.mwg.utility.Base64.encodeIntToBuffer(loopValue, buffer);
                                                break;
                                            case org.mwg.Type.DOUBLE_ARRAY:
                                                var castedDoubleArr = loopValue;
                                                org.mwg.utility.Base64.encodeIntToBuffer(castedDoubleArr.length, buffer);
                                                for (var j = 0; j < castedDoubleArr.length; j++) {
                                                    buffer.write(org.mwg.core.CoreConstants.CHUNK_SUB_SUB_SEP);
                                                    org.mwg.utility.Base64.encodeDoubleToBuffer(castedDoubleArr[j], buffer);
                                                }
                                                break;
                                            case org.mwg.Type.RELATION:
                                                var castedLongArrRel = loopValue;
                                                org.mwg.utility.Base64.encodeIntToBuffer(castedLongArrRel.size(), buffer);
                                                for (var j = 0; j < castedLongArrRel.size(); j++) {
                                                    buffer.write(org.mwg.core.CoreConstants.CHUNK_SUB_SUB_SEP);
                                                    org.mwg.utility.Base64.encodeLongToBuffer(castedLongArrRel.unsafe_get(j), buffer);
                                                }
                                                break;
                                            case org.mwg.Type.LONG_ARRAY:
                                                var castedLongArr = loopValue;
                                                org.mwg.utility.Base64.encodeIntToBuffer(castedLongArr.length, buffer);
                                                for (var j = 0; j < castedLongArr.length; j++) {
                                                    buffer.write(org.mwg.core.CoreConstants.CHUNK_SUB_SUB_SEP);
                                                    org.mwg.utility.Base64.encodeLongToBuffer(castedLongArr[j], buffer);
                                                }
                                                break;
                                            case org.mwg.Type.INT_ARRAY:
                                                var castedIntArr = loopValue;
                                                org.mwg.utility.Base64.encodeIntToBuffer(castedIntArr.length, buffer);
                                                for (var j = 0; j < castedIntArr.length; j++) {
                                                    buffer.write(org.mwg.core.CoreConstants.CHUNK_SUB_SUB_SEP);
                                                    org.mwg.utility.Base64.encodeIntToBuffer(castedIntArr[j], buffer);
                                                }
                                                break;
                                            case org.mwg.Type.STRING_TO_LONG_MAP:
                                                var castedStringLongMap = loopValue;
                                                org.mwg.utility.Base64.encodeLongToBuffer(castedStringLongMap.size(), buffer);
                                                castedStringLongMap.unsafe_each(function (key, value) {
                                                    {
                                                        buffer.write(org.mwg.core.CoreConstants.CHUNK_SUB_SUB_SEP);
                                                        org.mwg.utility.Base64.encodeStringToBuffer(key, buffer);
                                                        buffer.write(org.mwg.core.CoreConstants.CHUNK_SUB_SUB_SUB_SEP);
                                                        org.mwg.utility.Base64.encodeLongToBuffer(value, buffer);
                                                    }
                                                });
                                                break;
                                            case org.mwg.Type.LONG_TO_LONG_MAP:
                                                var castedLongLongMap = loopValue;
                                                org.mwg.utility.Base64.encodeLongToBuffer(castedLongLongMap.size(), buffer);
                                                castedLongLongMap.unsafe_each(function (key, value) {
                                                    {
                                                        buffer.write(org.mwg.core.CoreConstants.CHUNK_SUB_SUB_SEP);
                                                        org.mwg.utility.Base64.encodeLongToBuffer(key, buffer);
                                                        buffer.write(org.mwg.core.CoreConstants.CHUNK_SUB_SUB_SUB_SEP);
                                                        org.mwg.utility.Base64.encodeLongToBuffer(value, buffer);
                                                    }
                                                });
                                                break;
                                            case org.mwg.Type.LONG_TO_LONG_ARRAY_MAP:
                                                var castedLongLongArrayMap = loopValue;
                                                org.mwg.utility.Base64.encodeLongToBuffer(castedLongLongArrayMap.size(), buffer);
                                                castedLongLongArrayMap.unsafe_each(function (key, value) {
                                                    {
                                                        buffer.write(org.mwg.core.CoreConstants.CHUNK_SUB_SUB_SEP);
                                                        org.mwg.utility.Base64.encodeLongToBuffer(key, buffer);
                                                        buffer.write(org.mwg.core.CoreConstants.CHUNK_SUB_SUB_SUB_SEP);
                                                        org.mwg.utility.Base64.encodeLongToBuffer(value, buffer);
                                                    }
                                                });
                                                break;
                                            default:
                                                break;
                                        }
                                    }
                                }
                            }
                            this._dirty = false;
                        };
                        HeapStateChunk.prototype.each = function (callBack) {
                            for (var i = 0; i < this._size; i++) {
                                if (this._v[i] != null) {
                                    callBack(this._k[i], this._type[i], this._v[i]);
                                }
                            }
                        };
                        HeapStateChunk.prototype.loadFrom = function (origin) {
                            if (origin == null) {
                                return;
                            }
                            var casted = origin;
                            this._capacity = casted._capacity;
                            this._size = casted._size;
                            if (casted._k != null) {
                                var cloned_k = new Float64Array(this._capacity);
                                java.lang.System.arraycopy(casted._k, 0, cloned_k, 0, this._capacity);
                                this._k = cloned_k;
                            }
                            if (casted._type != null) {
                                var cloned_type = new Int8Array(this._capacity);
                                java.lang.System.arraycopy(casted._type, 0, cloned_type, 0, this._capacity);
                                this._type = cloned_type;
                            }
                            if (casted._next != null) {
                                var cloned_next = new Int32Array(this._capacity);
                                java.lang.System.arraycopy(casted._next, 0, cloned_next, 0, this._capacity);
                                this._next = cloned_next;
                            }
                            if (casted._hash != null) {
                                var cloned_hash = new Int32Array(this._capacity * 2);
                                java.lang.System.arraycopy(casted._hash, 0, cloned_hash, 0, this._capacity * 2);
                                this._hash = cloned_hash;
                            }
                            if (casted._v != null) {
                                this._v = new Array(this._capacity);
                                for (var i = 0; i < this._size; i++) {
                                    switch (casted._type[i]) {
                                        case org.mwg.Type.LONG_TO_LONG_MAP:
                                            if (casted._v[i] != null) {
                                                this._v[i] = casted._v[i].cloneFor(this);
                                            }
                                            break;
                                        case org.mwg.Type.LONG_TO_LONG_ARRAY_MAP:
                                            if (casted._v[i] != null) {
                                                this._v[i] = casted._v[i].cloneFor(this);
                                            }
                                            break;
                                        case org.mwg.Type.STRING_TO_LONG_MAP:
                                            if (casted._v[i] != null) {
                                                this._v[i] = casted._v[i].cloneFor(this);
                                            }
                                            break;
                                        case org.mwg.Type.RELATION:
                                            if (casted._v[i] != null) {
                                                this._v[i] = new org.mwg.core.chunk.heap.HeapRelationship(this, casted._v[i]);
                                            }
                                            break;
                                        default:
                                            this._v[i] = casted._v[i];
                                            break;
                                    }
                                }
                            }
                        };
                        HeapStateChunk.prototype.internal_set = function (p_key, p_type, p_unsafe_elem, replaceIfPresent, initial) {
                            var param_elem = null;
                            if (p_unsafe_elem != null) {
                                try {
                                    switch (p_type) {
                                        case org.mwg.Type.BOOL:
                                            param_elem = p_unsafe_elem;
                                            break;
                                        case org.mwg.Type.DOUBLE:
                                            param_elem = p_unsafe_elem;
                                            break;
                                        case org.mwg.Type.LONG:
                                            if (p_unsafe_elem instanceof Number) {
                                                var preCasting = p_unsafe_elem;
                                                param_elem = preCasting;
                                            }
                                            else {
                                                param_elem = p_unsafe_elem;
                                            }
                                            break;
                                        case org.mwg.Type.INT:
                                            param_elem = p_unsafe_elem;
                                            break;
                                        case org.mwg.Type.STRING:
                                            param_elem = p_unsafe_elem;
                                            break;
                                        case org.mwg.Type.RELATION:
                                            param_elem = p_unsafe_elem;
                                            break;
                                        case org.mwg.Type.DOUBLE_ARRAY:
                                            var castedParamDouble = p_unsafe_elem;
                                            var clonedDoubleArray = new Float64Array(castedParamDouble.length);
                                            java.lang.System.arraycopy(castedParamDouble, 0, clonedDoubleArray, 0, castedParamDouble.length);
                                            param_elem = clonedDoubleArray;
                                            break;
                                        case org.mwg.Type.LONG_ARRAY:
                                            var castedParamLong = p_unsafe_elem;
                                            var clonedLongArray = new Float64Array(castedParamLong.length);
                                            java.lang.System.arraycopy(castedParamLong, 0, clonedLongArray, 0, castedParamLong.length);
                                            param_elem = clonedLongArray;
                                            break;
                                        case org.mwg.Type.INT_ARRAY:
                                            var castedParamInt = p_unsafe_elem;
                                            var clonedIntArray = new Int32Array(castedParamInt.length);
                                            java.lang.System.arraycopy(castedParamInt, 0, clonedIntArray, 0, castedParamInt.length);
                                            param_elem = clonedIntArray;
                                            break;
                                        case org.mwg.Type.STRING_TO_LONG_MAP:
                                            param_elem = p_unsafe_elem;
                                            break;
                                        case org.mwg.Type.LONG_TO_LONG_MAP:
                                            param_elem = p_unsafe_elem;
                                            break;
                                        case org.mwg.Type.LONG_TO_LONG_ARRAY_MAP:
                                            param_elem = p_unsafe_elem;
                                            break;
                                        default:
                                            throw new Error("Internal Exception, unknown type");
                                    }
                                }
                                catch ($ex$) {
                                    if ($ex$ instanceof Error) {
                                        var e = $ex$;
                                        {
                                            throw new Error("mwDB usage error, set method called with type " + org.mwg.Type.typeName(p_type) + " while param object is " + p_unsafe_elem);
                                        }
                                    }
                                    else {
                                        throw $ex$;
                                    }
                                }
                            }
                            if (this._k == null) {
                                if (param_elem == null) {
                                    return;
                                }
                                this._capacity = org.mwg.Constants.MAP_INITIAL_CAPACITY;
                                this._k = new Float64Array(this._capacity);
                                this._v = new Array(this._capacity);
                                this._type = new Int8Array(this._capacity);
                                this._k[0] = p_key;
                                this._v[0] = param_elem;
                                this._type[0] = p_type;
                                this._size = 1;
                                return;
                            }
                            var entry = -1;
                            var p_entry = -1;
                            var hashIndex = -1;
                            if (this._hash == null) {
                                for (var i = 0; i < this._size; i++) {
                                    if (this._k[i] == p_key) {
                                        entry = i;
                                        break;
                                    }
                                }
                            }
                            else {
                                hashIndex = org.mwg.utility.HashHelper.longHash(p_key, this._capacity * 2);
                                var m = this._hash[hashIndex];
                                while (m != -1) {
                                    if (this._k[m] == p_key) {
                                        entry = m;
                                        break;
                                    }
                                    p_entry = m;
                                    m = this._next[m];
                                }
                            }
                            if (entry != -1) {
                                if (replaceIfPresent || (p_type != this._type[entry])) {
                                    if (param_elem == null) {
                                        if (this._hash != null) {
                                            if (p_entry != -1) {
                                                this._next[p_entry] = this._next[entry];
                                            }
                                            else {
                                                this._hash[hashIndex] = -1;
                                            }
                                        }
                                        var indexVictim = this._size - 1;
                                        if (entry == indexVictim) {
                                            this._k[entry] = -1;
                                            this._v[entry] = null;
                                            this._type[entry] = -1;
                                        }
                                        else {
                                            this._k[entry] = this._k[indexVictim];
                                            this._v[entry] = this._v[indexVictim];
                                            this._type[entry] = this._type[indexVictim];
                                            if (this._hash != null) {
                                                this._next[entry] = this._next[indexVictim];
                                                var victimHash = org.mwg.utility.HashHelper.longHash(this._k[entry], this._capacity * 2);
                                                var m = this._hash[victimHash];
                                                if (m == indexVictim) {
                                                    this._hash[victimHash] = entry;
                                                }
                                                else {
                                                    while (m != -1) {
                                                        if (this._next[m] == indexVictim) {
                                                            this._next[m] = entry;
                                                            break;
                                                        }
                                                        m = this._next[m];
                                                    }
                                                }
                                            }
                                        }
                                        this._size--;
                                    }
                                    else {
                                        this._v[entry] = param_elem;
                                        if (this._type[entry] != p_type) {
                                            this._type[entry] = p_type;
                                        }
                                    }
                                }
                                if (!initial) {
                                    this.declareDirty();
                                }
                                return;
                            }
                            if (this._size < this._capacity) {
                                this._k[this._size] = p_key;
                                this._v[this._size] = param_elem;
                                this._type[this._size] = p_type;
                                if (this._hash != null) {
                                    this._next[this._size] = this._hash[hashIndex];
                                    this._hash[hashIndex] = this._size;
                                }
                                this._size++;
                                this.declareDirty();
                                return;
                            }
                            var newCapacity = this._capacity * 2;
                            var ex_k = new Float64Array(newCapacity);
                            java.lang.System.arraycopy(this._k, 0, ex_k, 0, this._capacity);
                            this._k = ex_k;
                            var ex_v = new Array(newCapacity);
                            java.lang.System.arraycopy(this._v, 0, ex_v, 0, this._capacity);
                            this._v = ex_v;
                            var ex_type = new Int8Array(newCapacity);
                            java.lang.System.arraycopy(this._type, 0, ex_type, 0, this._capacity);
                            this._type = ex_type;
                            this._capacity = newCapacity;
                            this._k[this._size] = p_key;
                            this._v[this._size] = param_elem;
                            this._type[this._size] = p_type;
                            this._size++;
                            this._hash = new Int32Array(this._capacity * 2);
                            java.util.Arrays.fill(this._hash, 0, this._capacity * 2, -1);
                            this._next = new Int32Array(this._capacity);
                            java.util.Arrays.fill(this._next, 0, this._capacity, -1);
                            for (var i = 0; i < this._size; i++) {
                                var keyHash = org.mwg.utility.HashHelper.longHash(this._k[i], this._capacity * 2);
                                this._next[i] = this._hash[keyHash];
                                this._hash[keyHash] = i;
                            }
                            if (!initial) {
                                this.declareDirty();
                            }
                        };
                        HeapStateChunk.prototype.allocate = function (newCapacity) {
                            if (newCapacity <= this._capacity) {
                                return;
                            }
                            var ex_k = new Float64Array(newCapacity);
                            if (this._k != null) {
                                java.lang.System.arraycopy(this._k, 0, ex_k, 0, this._capacity);
                            }
                            this._k = ex_k;
                            var ex_v = new Array(newCapacity);
                            if (this._v != null) {
                                java.lang.System.arraycopy(this._v, 0, ex_v, 0, this._capacity);
                            }
                            this._v = ex_v;
                            var ex_type = new Int8Array(newCapacity);
                            if (this._type != null) {
                                java.lang.System.arraycopy(this._type, 0, ex_type, 0, this._capacity);
                            }
                            this._type = ex_type;
                            this._capacity = newCapacity;
                            this._hash = new Int32Array(this._capacity * 2);
                            java.util.Arrays.fill(this._hash, 0, this._capacity * 2, -1);
                            this._next = new Int32Array(this._capacity);
                            java.util.Arrays.fill(this._next, 0, this._capacity, -1);
                            for (var i = 0; i < this._size; i++) {
                                var keyHash = org.mwg.utility.HashHelper.longHash(this._k[i], this._capacity * 2);
                                this._next[i] = this._hash[keyHash];
                                this._hash[keyHash] = i;
                            }
                        };
                        HeapStateChunk.prototype.load = function (buffer) {
                            if (buffer == null || buffer.length() == 0) {
                                return;
                            }
                            var initial = this._k == null;
                            var cursor = 0;
                            var payloadSize = buffer.length();
                            var previousStart = -1;
                            var currentChunkElemKey = org.mwg.core.CoreConstants.NULL_LONG;
                            var currentChunkElemType = -1;
                            var isFirstElem = true;
                            var currentDoubleArr = null;
                            var currentLongArr = null;
                            var currentIntArr = null;
                            var currentRelation = null;
                            var currentStringLongMap = null;
                            var currentLongLongMap = null;
                            var currentLongLongArrayMap = null;
                            var currentSubSize = -1;
                            var currentSubIndex = 0;
                            var currentMapLongKey = org.mwg.core.CoreConstants.NULL_LONG;
                            var currentMapStringKey = null;
                            while (cursor < payloadSize) {
                                var current = buffer.read(cursor);
                                if (current == org.mwg.core.CoreConstants.CHUNK_SEP) {
                                    if (isFirstElem) {
                                        isFirstElem = false;
                                        var stateChunkSize = org.mwg.utility.Base64.decodeToIntWithBounds(buffer, 0, cursor);
                                        var closePowerOfTwo = Math.pow(2, Math.ceil(Math.log(stateChunkSize) / Math.log(2)));
                                        this.allocate(closePowerOfTwo);
                                        previousStart = cursor + 1;
                                    }
                                    else {
                                        if (currentChunkElemType != -1) {
                                            var toInsert = null;
                                            switch (currentChunkElemType) {
                                                case org.mwg.Type.BOOL:
                                                    if (buffer.read(previousStart) == org.mwg.core.CoreConstants.BOOL_FALSE) {
                                                        toInsert = false;
                                                    }
                                                    else if (buffer.read(previousStart) == org.mwg.core.CoreConstants.BOOL_TRUE) {
                                                        toInsert = true;
                                                    }
                                                    break;
                                                case org.mwg.Type.STRING:
                                                    toInsert = org.mwg.utility.Base64.decodeToStringWithBounds(buffer, previousStart, cursor);
                                                    break;
                                                case org.mwg.Type.DOUBLE:
                                                    toInsert = org.mwg.utility.Base64.decodeToDoubleWithBounds(buffer, previousStart, cursor);
                                                    break;
                                                case org.mwg.Type.LONG:
                                                    toInsert = org.mwg.utility.Base64.decodeToLongWithBounds(buffer, previousStart, cursor);
                                                    break;
                                                case org.mwg.Type.INT:
                                                    toInsert = org.mwg.utility.Base64.decodeToIntWithBounds(buffer, previousStart, cursor);
                                                    break;
                                                case org.mwg.Type.DOUBLE_ARRAY:
                                                    if (currentDoubleArr == null) {
                                                        currentDoubleArr = new Float64Array(org.mwg.utility.Base64.decodeToIntWithBounds(buffer, previousStart, cursor));
                                                    }
                                                    else {
                                                        currentDoubleArr[currentSubIndex] = org.mwg.utility.Base64.decodeToDoubleWithBounds(buffer, previousStart, cursor);
                                                    }
                                                    toInsert = currentDoubleArr;
                                                    break;
                                                case org.mwg.Type.LONG_ARRAY:
                                                    if (currentLongArr == null) {
                                                        currentLongArr = new Float64Array(org.mwg.utility.Base64.decodeToIntWithBounds(buffer, previousStart, cursor));
                                                    }
                                                    else {
                                                        currentLongArr[currentSubIndex] = org.mwg.utility.Base64.decodeToLongWithBounds(buffer, previousStart, cursor);
                                                    }
                                                    toInsert = currentLongArr;
                                                    break;
                                                case org.mwg.Type.INT_ARRAY:
                                                    if (currentIntArr == null) {
                                                        currentIntArr = new Int32Array(org.mwg.utility.Base64.decodeToIntWithBounds(buffer, previousStart, cursor));
                                                    }
                                                    else {
                                                        currentIntArr[currentSubIndex] = org.mwg.utility.Base64.decodeToIntWithBounds(buffer, previousStart, cursor);
                                                    }
                                                    toInsert = currentIntArr;
                                                    break;
                                                case org.mwg.Type.RELATION:
                                                    if (currentRelation == null) {
                                                        currentRelation = new org.mwg.core.chunk.heap.HeapRelationship(this, null);
                                                        currentRelation.allocate(org.mwg.utility.Base64.decodeToIntWithBounds(buffer, previousStart, cursor));
                                                    }
                                                    else {
                                                        currentRelation.add(org.mwg.utility.Base64.decodeToLongWithBounds(buffer, previousStart, cursor));
                                                    }
                                                    toInsert = currentRelation;
                                                    break;
                                                case org.mwg.Type.STRING_TO_LONG_MAP:
                                                    if (currentMapStringKey != null) {
                                                        currentStringLongMap.put(currentMapStringKey, org.mwg.utility.Base64.decodeToLongWithBounds(buffer, previousStart, cursor));
                                                    }
                                                    toInsert = currentStringLongMap;
                                                    break;
                                                case org.mwg.Type.LONG_TO_LONG_MAP:
                                                    if (currentMapLongKey != org.mwg.core.CoreConstants.NULL_LONG) {
                                                        currentLongLongMap.put(currentMapLongKey, org.mwg.utility.Base64.decodeToLongWithBounds(buffer, previousStart, cursor));
                                                    }
                                                    toInsert = currentLongLongMap;
                                                    break;
                                                case org.mwg.Type.LONG_TO_LONG_ARRAY_MAP:
                                                    if (currentMapLongKey != org.mwg.core.CoreConstants.NULL_LONG) {
                                                        currentLongLongArrayMap.put(currentMapLongKey, org.mwg.utility.Base64.decodeToLongWithBounds(buffer, previousStart, cursor));
                                                    }
                                                    toInsert = currentLongLongArrayMap;
                                                    break;
                                            }
                                            if (toInsert != null) {
                                                this.internal_set(currentChunkElemKey, currentChunkElemType, toInsert, true, initial);
                                            }
                                        }
                                        previousStart = cursor + 1;
                                        currentChunkElemKey = org.mwg.core.CoreConstants.NULL_LONG;
                                        currentChunkElemType = -1;
                                        currentSubSize = -1;
                                        currentSubIndex = 0;
                                        currentMapLongKey = org.mwg.core.CoreConstants.NULL_LONG;
                                        currentMapStringKey = null;
                                    }
                                }
                                else if (current == org.mwg.core.CoreConstants.CHUNK_SUB_SEP) {
                                    if (currentChunkElemKey == org.mwg.core.CoreConstants.NULL_LONG) {
                                        currentChunkElemKey = org.mwg.utility.Base64.decodeToLongWithBounds(buffer, previousStart, cursor);
                                        previousStart = cursor + 1;
                                    }
                                    else if (currentChunkElemType == -1) {
                                        currentChunkElemType = org.mwg.utility.Base64.decodeToIntWithBounds(buffer, previousStart, cursor);
                                        previousStart = cursor + 1;
                                    }
                                }
                                else if (current == org.mwg.core.CoreConstants.CHUNK_SUB_SUB_SEP) {
                                    if (currentSubSize == -1) {
                                        currentSubSize = org.mwg.utility.Base64.decodeToLongWithBounds(buffer, previousStart, cursor);
                                        switch (currentChunkElemType) {
                                            case org.mwg.Type.DOUBLE_ARRAY:
                                                currentDoubleArr = new Float64Array(currentSubSize);
                                                break;
                                            case org.mwg.Type.LONG_ARRAY:
                                                currentLongArr = new Float64Array(currentSubSize);
                                                break;
                                            case org.mwg.Type.INT_ARRAY:
                                                currentIntArr = new Int32Array(currentSubSize);
                                                break;
                                            case org.mwg.Type.RELATION:
                                                currentRelation = new org.mwg.core.chunk.heap.HeapRelationship(this, null);
                                                currentRelation.allocate(currentSubSize);
                                                break;
                                            case org.mwg.Type.STRING_TO_LONG_MAP:
                                                currentStringLongMap = new org.mwg.core.chunk.heap.HeapStringLongMap(this);
                                                currentStringLongMap.reallocate(currentSubSize);
                                                break;
                                            case org.mwg.Type.LONG_TO_LONG_MAP:
                                                currentLongLongMap = new org.mwg.core.chunk.heap.HeapLongLongMap(this);
                                                currentLongLongMap.reallocate(currentSubSize);
                                                break;
                                            case org.mwg.Type.LONG_TO_LONG_ARRAY_MAP:
                                                currentLongLongArrayMap = new org.mwg.core.chunk.heap.HeapLongLongArrayMap(this);
                                                currentLongLongArrayMap.reallocate(currentSubSize);
                                                break;
                                        }
                                    }
                                    else {
                                        switch (currentChunkElemType) {
                                            case org.mwg.Type.DOUBLE_ARRAY:
                                                currentDoubleArr[currentSubIndex] = org.mwg.utility.Base64.decodeToDoubleWithBounds(buffer, previousStart, cursor);
                                                currentSubIndex++;
                                                break;
                                            case org.mwg.Type.RELATION:
                                                currentRelation.add(org.mwg.utility.Base64.decodeToLongWithBounds(buffer, previousStart, cursor));
                                                break;
                                            case org.mwg.Type.LONG_ARRAY:
                                                currentLongArr[currentSubIndex] = org.mwg.utility.Base64.decodeToLongWithBounds(buffer, previousStart, cursor);
                                                currentSubIndex++;
                                                break;
                                            case org.mwg.Type.INT_ARRAY:
                                                currentIntArr[currentSubIndex] = org.mwg.utility.Base64.decodeToIntWithBounds(buffer, previousStart, cursor);
                                                currentSubIndex++;
                                                break;
                                            case org.mwg.Type.STRING_TO_LONG_MAP:
                                                if (currentMapStringKey != null) {
                                                    currentStringLongMap.put(currentMapStringKey, org.mwg.utility.Base64.decodeToLongWithBounds(buffer, previousStart, cursor));
                                                    currentMapStringKey = null;
                                                }
                                                break;
                                            case org.mwg.Type.LONG_TO_LONG_MAP:
                                                if (currentMapLongKey != org.mwg.core.CoreConstants.NULL_LONG) {
                                                    currentLongLongMap.put(currentMapLongKey, org.mwg.utility.Base64.decodeToLongWithBounds(buffer, previousStart, cursor));
                                                    currentMapLongKey = org.mwg.core.CoreConstants.NULL_LONG;
                                                }
                                                break;
                                            case org.mwg.Type.LONG_TO_LONG_ARRAY_MAP:
                                                if (currentMapLongKey != org.mwg.core.CoreConstants.NULL_LONG) {
                                                    currentLongLongArrayMap.put(currentMapLongKey, org.mwg.utility.Base64.decodeToLongWithBounds(buffer, previousStart, cursor));
                                                    currentMapLongKey = org.mwg.core.CoreConstants.NULL_LONG;
                                                }
                                                break;
                                        }
                                    }
                                    previousStart = cursor + 1;
                                }
                                else if (current == org.mwg.core.CoreConstants.CHUNK_SUB_SUB_SUB_SEP) {
                                    switch (currentChunkElemType) {
                                        case org.mwg.Type.STRING_TO_LONG_MAP:
                                            if (currentMapStringKey == null) {
                                                currentMapStringKey = org.mwg.utility.Base64.decodeToStringWithBounds(buffer, previousStart, cursor);
                                            }
                                            else {
                                                currentStringLongMap.put(currentMapStringKey, org.mwg.utility.Base64.decodeToLongWithBounds(buffer, previousStart, cursor));
                                                currentMapStringKey = null;
                                            }
                                            break;
                                        case org.mwg.Type.LONG_TO_LONG_MAP:
                                            if (currentMapLongKey == org.mwg.core.CoreConstants.NULL_LONG) {
                                                currentMapLongKey = org.mwg.utility.Base64.decodeToLongWithBounds(buffer, previousStart, cursor);
                                            }
                                            else {
                                                currentLongLongMap.put(currentMapLongKey, org.mwg.utility.Base64.decodeToLongWithBounds(buffer, previousStart, cursor));
                                                currentMapLongKey = org.mwg.core.CoreConstants.NULL_LONG;
                                            }
                                            break;
                                        case org.mwg.Type.LONG_TO_LONG_ARRAY_MAP:
                                            if (currentMapLongKey == org.mwg.core.CoreConstants.NULL_LONG) {
                                                currentMapLongKey = org.mwg.utility.Base64.decodeToLongWithBounds(buffer, previousStart, cursor);
                                            }
                                            else {
                                                currentLongLongArrayMap.put(currentMapLongKey, org.mwg.utility.Base64.decodeToLongWithBounds(buffer, previousStart, cursor));
                                                currentMapLongKey = org.mwg.core.CoreConstants.NULL_LONG;
                                            }
                                            break;
                                    }
                                    previousStart = cursor + 1;
                                }
                                cursor++;
                            }
                            if (currentChunkElemType != -1) {
                                var toInsert = null;
                                switch (currentChunkElemType) {
                                    case org.mwg.Type.BOOL:
                                        if (buffer.read(previousStart) == org.mwg.core.CoreConstants.BOOL_FALSE) {
                                            toInsert = false;
                                        }
                                        else if (buffer.read(previousStart) == org.mwg.core.CoreConstants.BOOL_TRUE) {
                                            toInsert = true;
                                        }
                                        break;
                                    case org.mwg.Type.STRING:
                                        toInsert = org.mwg.utility.Base64.decodeToStringWithBounds(buffer, previousStart, cursor);
                                        break;
                                    case org.mwg.Type.DOUBLE:
                                        toInsert = org.mwg.utility.Base64.decodeToDoubleWithBounds(buffer, previousStart, cursor);
                                        break;
                                    case org.mwg.Type.LONG:
                                        toInsert = org.mwg.utility.Base64.decodeToLongWithBounds(buffer, previousStart, cursor);
                                        break;
                                    case org.mwg.Type.INT:
                                        toInsert = org.mwg.utility.Base64.decodeToIntWithBounds(buffer, previousStart, cursor);
                                        break;
                                    case org.mwg.Type.DOUBLE_ARRAY:
                                        if (currentDoubleArr == null) {
                                            currentDoubleArr = new Float64Array(org.mwg.utility.Base64.decodeToIntWithBounds(buffer, previousStart, cursor));
                                        }
                                        else {
                                            currentDoubleArr[currentSubIndex] = org.mwg.utility.Base64.decodeToDoubleWithBounds(buffer, previousStart, cursor);
                                        }
                                        toInsert = currentDoubleArr;
                                        break;
                                    case org.mwg.Type.LONG_ARRAY:
                                        if (currentLongArr == null) {
                                            currentLongArr = new Float64Array(org.mwg.utility.Base64.decodeToIntWithBounds(buffer, previousStart, cursor));
                                        }
                                        else {
                                            currentLongArr[currentSubIndex] = org.mwg.utility.Base64.decodeToLongWithBounds(buffer, previousStart, cursor);
                                        }
                                        toInsert = currentLongArr;
                                        break;
                                    case org.mwg.Type.INT_ARRAY:
                                        if (currentIntArr == null) {
                                            currentIntArr = new Int32Array(org.mwg.utility.Base64.decodeToIntWithBounds(buffer, previousStart, cursor));
                                        }
                                        else {
                                            currentIntArr[currentSubIndex] = org.mwg.utility.Base64.decodeToIntWithBounds(buffer, previousStart, cursor);
                                        }
                                        toInsert = currentIntArr;
                                        break;
                                    case org.mwg.Type.RELATION:
                                        if (currentRelation != null) {
                                            currentRelation.add(org.mwg.utility.Base64.decodeToIntWithBounds(buffer, previousStart, cursor));
                                        }
                                        toInsert = currentRelation;
                                        break;
                                    case org.mwg.Type.STRING_TO_LONG_MAP:
                                        if (currentMapStringKey != null) {
                                            currentStringLongMap.put(currentMapStringKey, org.mwg.utility.Base64.decodeToLongWithBounds(buffer, previousStart, cursor));
                                        }
                                        toInsert = currentStringLongMap;
                                        break;
                                    case org.mwg.Type.LONG_TO_LONG_MAP:
                                        if (currentMapLongKey != org.mwg.core.CoreConstants.NULL_LONG) {
                                            currentLongLongMap.put(currentMapLongKey, org.mwg.utility.Base64.decodeToLongWithBounds(buffer, previousStart, cursor));
                                        }
                                        toInsert = currentLongLongMap;
                                        break;
                                    case org.mwg.Type.LONG_TO_LONG_ARRAY_MAP:
                                        if (currentMapLongKey != org.mwg.core.CoreConstants.NULL_LONG) {
                                            currentLongLongArrayMap.put(currentMapLongKey, org.mwg.utility.Base64.decodeToLongWithBounds(buffer, previousStart, cursor));
                                        }
                                        toInsert = currentLongLongArrayMap;
                                        break;
                                }
                                if (toInsert != null) {
                                    this.internal_set(currentChunkElemKey, currentChunkElemType, toInsert, true, initial);
                                }
                            }
                        };
                        return HeapStateChunk;
                    }());
                    heap.HeapStateChunk = HeapStateChunk;
                    var HeapStringLongMap = (function () {
                        function HeapStringLongMap(p_listener) {
                            this.mapSize = 0;
                            this.capacity = 0;
                            this.keys = null;
                            this.keysH = null;
                            this.values = null;
                            this.nexts = null;
                            this.hashs = null;
                            this.parent = p_listener;
                        }
                        HeapStringLongMap.prototype.key = function (i) {
                            return this.keys[i];
                        };
                        HeapStringLongMap.prototype.setKey = function (i, newValue) {
                            this.keys[i] = newValue;
                        };
                        HeapStringLongMap.prototype.keyH = function (i) {
                            return this.keysH[i];
                        };
                        HeapStringLongMap.prototype.setKeyH = function (i, newValue) {
                            this.keysH[i] = newValue;
                        };
                        HeapStringLongMap.prototype.value = function (i) {
                            return this.values[i];
                        };
                        HeapStringLongMap.prototype.setValue = function (i, newValue) {
                            this.values[i] = newValue;
                        };
                        HeapStringLongMap.prototype.next = function (i) {
                            return this.nexts[i];
                        };
                        HeapStringLongMap.prototype.setNext = function (i, newValue) {
                            this.nexts[i] = newValue;
                        };
                        HeapStringLongMap.prototype.hash = function (i) {
                            return this.hashs[i];
                        };
                        HeapStringLongMap.prototype.setHash = function (i, newValue) {
                            this.hashs[i] = newValue;
                        };
                        HeapStringLongMap.prototype.reallocate = function (newCapacity) {
                            if (newCapacity > this.capacity) {
                                var new_keys = new Array(newCapacity);
                                if (this.keys != null) {
                                    java.lang.System.arraycopy(this.keys, 0, new_keys, 0, this.capacity);
                                }
                                this.keys = new_keys;
                                var new_keysH = new Float64Array(newCapacity);
                                if (this.keysH != null) {
                                    java.lang.System.arraycopy(this.keysH, 0, new_keysH, 0, this.capacity);
                                }
                                this.keysH = new_keysH;
                                var new_values = new Float64Array(newCapacity);
                                if (this.values != null) {
                                    java.lang.System.arraycopy(this.values, 0, new_values, 0, this.capacity);
                                }
                                this.values = new_values;
                                var new_nexts = new Int32Array(newCapacity);
                                var new_hashes = new Int32Array(newCapacity * 2);
                                java.util.Arrays.fill(new_nexts, 0, newCapacity, -1);
                                java.util.Arrays.fill(new_hashes, 0, newCapacity * 2, -1);
                                this.hashs = new_hashes;
                                this.nexts = new_nexts;
                                for (var i = 0; i < this.mapSize; i++) {
                                    var new_key_hash = org.mwg.utility.HashHelper.longHash(this.keyH(i), newCapacity * 2);
                                    this.setNext(i, this.hash(new_key_hash));
                                    this.setHash(new_key_hash, i);
                                }
                                this.capacity = newCapacity;
                            }
                        };
                        HeapStringLongMap.prototype.cloneFor = function (newParent) {
                            var cloned = new org.mwg.core.chunk.heap.HeapStringLongMap(newParent);
                            cloned.mapSize = this.mapSize;
                            cloned.capacity = this.capacity;
                            if (this.keys != null) {
                                var cloned_keys = new Array(this.capacity);
                                java.lang.System.arraycopy(this.keys, 0, cloned_keys, 0, this.capacity);
                                cloned.keys = cloned_keys;
                            }
                            if (this.keysH != null) {
                                var cloned_keysH = new Float64Array(this.capacity);
                                java.lang.System.arraycopy(this.keysH, 0, cloned_keysH, 0, this.capacity);
                                cloned.keysH = cloned_keysH;
                            }
                            if (this.values != null) {
                                var cloned_values = new Float64Array(this.capacity);
                                java.lang.System.arraycopy(this.values, 0, cloned_values, 0, this.capacity);
                                cloned.values = cloned_values;
                            }
                            if (this.nexts != null) {
                                var cloned_nexts = new Int32Array(this.capacity);
                                java.lang.System.arraycopy(this.nexts, 0, cloned_nexts, 0, this.capacity);
                                cloned.nexts = cloned_nexts;
                            }
                            if (this.hashs != null) {
                                var cloned_hashs = new Int32Array(this.capacity * 2);
                                java.lang.System.arraycopy(this.hashs, 0, cloned_hashs, 0, this.capacity * 2);
                                cloned.hashs = cloned_hashs;
                            }
                            return cloned;
                        };
                        HeapStringLongMap.prototype.getValue = function (requestString) {
                            var result = org.mwg.Constants.NULL_LONG;
                            {
                                if (this.keys != null) {
                                    var keyHash = org.mwg.utility.HashHelper.hash(requestString);
                                    var hashIndex = org.mwg.utility.HashHelper.longHash(keyHash, this.capacity * 2);
                                    var m = this.hash(hashIndex);
                                    while (m >= 0) {
                                        if (keyHash == this.keyH(m)) {
                                            result = this.value(m);
                                            break;
                                        }
                                        m = this.next(m);
                                    }
                                }
                            }
                            return result;
                        };
                        HeapStringLongMap.prototype.getByHash = function (keyHash) {
                            var result = null;
                            {
                                if (this.keys != null) {
                                    var hashIndex = org.mwg.utility.HashHelper.longHash(keyHash, this.capacity * 2);
                                    var m = this.hash(hashIndex);
                                    while (m >= 0) {
                                        if (keyHash == this.keyH(m)) {
                                            result = this.key(m);
                                            break;
                                        }
                                        m = this.next(m);
                                    }
                                }
                            }
                            return result;
                        };
                        HeapStringLongMap.prototype.containsHash = function (keyHash) {
                            var result = false;
                            {
                                if (this.keys != null) {
                                    var hashIndex = org.mwg.utility.HashHelper.longHash(keyHash, this.capacity * 2);
                                    var m = this.hash(hashIndex);
                                    while (m >= 0) {
                                        if (keyHash == this.keyH(m)) {
                                            result = true;
                                            break;
                                        }
                                        m = this.next(m);
                                    }
                                }
                            }
                            return result;
                        };
                        HeapStringLongMap.prototype.each = function (callback) {
                            {
                                this.unsafe_each(callback);
                            }
                        };
                        HeapStringLongMap.prototype.unsafe_each = function (callback) {
                            for (var i = 0; i < this.mapSize; i++) {
                                callback(this.key(i), this.value(i));
                            }
                        };
                        HeapStringLongMap.prototype.size = function () {
                            var result;
                            {
                                result = this.mapSize;
                            }
                            return result;
                        };
                        HeapStringLongMap.prototype.remove = function (requestKey) {
                            {
                                if (this.keys != null && this.mapSize != 0) {
                                    var keyHash = org.mwg.utility.HashHelper.hash(requestKey);
                                    var hashCapacity = this.capacity * 2;
                                    var hashIndex = org.mwg.utility.HashHelper.longHash(keyHash, hashCapacity);
                                    var m = this.hash(hashIndex);
                                    var found = -1;
                                    while (m >= 0) {
                                        if (requestKey == this.key(m)) {
                                            found = m;
                                            break;
                                        }
                                        m = this.next(m);
                                    }
                                    if (found != -1) {
                                        var toRemoveHash = org.mwg.utility.HashHelper.longHash(keyHash, hashCapacity);
                                        m = this.hash(toRemoveHash);
                                        if (m == found) {
                                            this.setHash(toRemoveHash, this.next(m));
                                        }
                                        else {
                                            while (m != -1) {
                                                var next_of_m = this.next(m);
                                                if (next_of_m == found) {
                                                    this.setNext(m, this.next(next_of_m));
                                                    break;
                                                }
                                                m = next_of_m;
                                            }
                                        }
                                        var lastIndex = this.mapSize - 1;
                                        if (lastIndex == found) {
                                            this.mapSize--;
                                        }
                                        else {
                                            var lastKey = this.key(lastIndex);
                                            var lastKeyH = this.keyH(lastIndex);
                                            this.setKey(found, lastKey);
                                            this.setKeyH(found, lastKeyH);
                                            this.setValue(found, this.value(lastIndex));
                                            this.setNext(found, this.next(lastIndex));
                                            var victimHash = org.mwg.utility.HashHelper.longHash(lastKeyH, hashCapacity);
                                            m = this.hash(victimHash);
                                            if (m == lastIndex) {
                                                this.setHash(victimHash, found);
                                            }
                                            else {
                                                while (m != -1) {
                                                    var next_of_m = this.next(m);
                                                    if (next_of_m == lastIndex) {
                                                        this.setNext(m, found);
                                                        break;
                                                    }
                                                    m = next_of_m;
                                                }
                                            }
                                            this.mapSize--;
                                        }
                                        this.parent.declareDirty();
                                    }
                                }
                            }
                        };
                        HeapStringLongMap.prototype.put = function (insertKey, insertValue) {
                            {
                                var keyHash = org.mwg.utility.HashHelper.hash(insertKey);
                                if (this.keys == null) {
                                    this.reallocate(org.mwg.Constants.MAP_INITIAL_CAPACITY);
                                    this.setKey(0, insertKey);
                                    this.setKeyH(0, keyHash);
                                    this.setValue(0, insertValue);
                                    this.setHash(org.mwg.utility.HashHelper.longHash(keyHash, this.capacity * 2), 0);
                                    this.setNext(0, -1);
                                    this.mapSize++;
                                }
                                else {
                                    var hashCapacity = this.capacity * 2;
                                    var insertKeyHash = org.mwg.utility.HashHelper.longHash(keyHash, hashCapacity);
                                    var currentHash = this.hash(insertKeyHash);
                                    var m = currentHash;
                                    var found = -1;
                                    while (m >= 0) {
                                        if (insertKey == this.key(m)) {
                                            found = m;
                                            break;
                                        }
                                        m = this.next(m);
                                    }
                                    if (found == -1) {
                                        var lastIndex = this.mapSize;
                                        if (lastIndex == this.capacity) {
                                            this.reallocate(this.capacity * 2);
                                        }
                                        this.setKey(lastIndex, insertKey);
                                        this.setKeyH(lastIndex, keyHash);
                                        this.setValue(lastIndex, insertValue);
                                        this.setHash(org.mwg.utility.HashHelper.longHash(keyHash, this.capacity * 2), lastIndex);
                                        this.setNext(lastIndex, currentHash);
                                        this.mapSize++;
                                        this.parent.declareDirty();
                                    }
                                    else {
                                        if (this.value(found) != insertValue) {
                                            this.setValue(found, insertValue);
                                            this.parent.declareDirty();
                                        }
                                    }
                                }
                            }
                        };
                        return HeapStringLongMap;
                    }());
                    heap.HeapStringLongMap = HeapStringLongMap;
                    var HeapTimeTreeChunk = (function () {
                        function HeapTimeTreeChunk(p_space, p_index) {
                            this._root = -1;
                            this._size = 0;
                            this._space = p_space;
                            this._index = p_index;
                            this._magic = 0;
                            this._dirty = false;
                        }
                        HeapTimeTreeChunk.prototype.world = function () {
                            return this._space.worldByIndex(this._index);
                        };
                        HeapTimeTreeChunk.prototype.time = function () {
                            return this._space.timeByIndex(this._index);
                        };
                        HeapTimeTreeChunk.prototype.id = function () {
                            return this._space.idByIndex(this._index);
                        };
                        HeapTimeTreeChunk.prototype.size = function () {
                            return this._size;
                        };
                        HeapTimeTreeChunk.prototype.range = function (startKey, endKey, maxElements, walker) {
                            var nbElements = 0;
                            var indexEnd = this.internal_previousOrEqual_index(endKey);
                            while (indexEnd != -1 && this.key(indexEnd) >= startKey && nbElements < maxElements) {
                                walker(this.key(indexEnd));
                                nbElements++;
                                indexEnd = this.previous(indexEnd);
                            }
                        };
                        HeapTimeTreeChunk.prototype.save = function (buffer) {
                            org.mwg.utility.Base64.encodeLongToBuffer(this._size, buffer);
                            buffer.write(org.mwg.core.CoreConstants.CHUNK_SEP);
                            var isFirst = true;
                            for (var i = 0; i < this._size; i++) {
                                if (!isFirst) {
                                    buffer.write(org.mwg.core.CoreConstants.CHUNK_SUB_SEP);
                                }
                                else {
                                    isFirst = false;
                                }
                                org.mwg.utility.Base64.encodeLongToBuffer(this._k[i], buffer);
                            }
                            this._dirty = false;
                        };
                        HeapTimeTreeChunk.prototype.load = function (buffer) {
                            if (buffer == null || buffer.length() == 0) {
                                return;
                            }
                            var initial = this._k == null;
                            var isDirty = false;
                            var cursor = 0;
                            var previous = 0;
                            var payloadSize = buffer.length();
                            while (cursor < payloadSize) {
                                var current = buffer.read(cursor);
                                if (current == org.mwg.core.CoreConstants.CHUNK_SUB_SEP) {
                                    var insertResult_1 = this.internal_insert(org.mwg.utility.Base64.decodeToLongWithBounds(buffer, previous, cursor));
                                    isDirty = isDirty || insertResult_1;
                                    previous = cursor + 1;
                                }
                                else if (current == org.mwg.core.CoreConstants.CHUNK_SEP) {
                                    this.reallocate(org.mwg.utility.Base64.decodeToLongWithBounds(buffer, previous, cursor));
                                    previous = cursor + 1;
                                }
                                cursor++;
                            }
                            var insertResult = this.internal_insert(org.mwg.utility.Base64.decodeToLongWithBounds(buffer, previous, cursor));
                            isDirty = isDirty || insertResult;
                            if (isDirty && !initial && !this._dirty) {
                                this._dirty = true;
                                if (this._space != null) {
                                    this._space.notifyUpdate(this._index);
                                }
                            }
                        };
                        HeapTimeTreeChunk.prototype.index = function () {
                            return this._index;
                        };
                        HeapTimeTreeChunk.prototype.previousOrEqual = function (key) {
                            var resultKey;
                            var result = this.internal_previousOrEqual_index(key);
                            if (result != -1) {
                                resultKey = this.key(result);
                            }
                            else {
                                resultKey = org.mwg.core.CoreConstants.NULL_LONG;
                            }
                            return resultKey;
                        };
                        HeapTimeTreeChunk.prototype.magic = function () {
                            return this._magic;
                        };
                        HeapTimeTreeChunk.prototype.insert = function (p_key) {
                            if (this.internal_insert(p_key)) {
                                this.internal_set_dirty();
                            }
                        };
                        HeapTimeTreeChunk.prototype.unsafe_insert = function (p_key) {
                            this.internal_insert(p_key);
                        };
                        HeapTimeTreeChunk.prototype.chunkType = function () {
                            return org.mwg.chunk.ChunkType.TIME_TREE_CHUNK;
                        };
                        HeapTimeTreeChunk.prototype.clearAt = function (max) {
                            var previousValue = this._k;
                            this._k = new Float64Array(this._k.length);
                            this._back_meta = new Int32Array(this._k.length * HeapTimeTreeChunk.META_SIZE);
                            this._colors = [];
                            this._root = -1;
                            var _previousSize = this._size;
                            this._size = 0;
                            for (var i = 0; i < _previousSize; i++) {
                                if (previousValue[i] != org.mwg.core.CoreConstants.NULL_LONG && previousValue[i] < max) {
                                    this.internal_insert(previousValue[i]);
                                }
                            }
                            this.internal_set_dirty();
                        };
                        HeapTimeTreeChunk.prototype.reallocate = function (newCapacity) {
                            var new_back_kv = new Float64Array(newCapacity);
                            if (this._k != null) {
                                java.lang.System.arraycopy(this._k, 0, new_back_kv, 0, this._size);
                            }
                            var new_back_colors = [];
                            if (this._colors != null) {
                                java.lang.System.arraycopy(this._colors, 0, new_back_colors, 0, this._size);
                                for (var i = this._size; i < newCapacity; i++) {
                                    new_back_colors[i] = false;
                                }
                            }
                            var new_back_meta = new Int32Array(newCapacity * HeapTimeTreeChunk.META_SIZE);
                            if (this._back_meta != null) {
                                java.lang.System.arraycopy(this._back_meta, 0, new_back_meta, 0, this._size * HeapTimeTreeChunk.META_SIZE);
                                for (var i = this._size * HeapTimeTreeChunk.META_SIZE; i < newCapacity * HeapTimeTreeChunk.META_SIZE; i++) {
                                    new_back_meta[i] = -1;
                                }
                            }
                            this._back_meta = new_back_meta;
                            this._k = new_back_kv;
                            this._colors = new_back_colors;
                        };
                        HeapTimeTreeChunk.prototype.key = function (p_currentIndex) {
                            if (p_currentIndex == -1) {
                                return -1;
                            }
                            return this._k[p_currentIndex];
                        };
                        HeapTimeTreeChunk.prototype.setKey = function (p_currentIndex, p_paramIndex) {
                            this._k[p_currentIndex] = p_paramIndex;
                        };
                        HeapTimeTreeChunk.prototype.left = function (p_currentIndex) {
                            if (p_currentIndex == -1) {
                                return -1;
                            }
                            return this._back_meta[p_currentIndex * HeapTimeTreeChunk.META_SIZE];
                        };
                        HeapTimeTreeChunk.prototype.setLeft = function (p_currentIndex, p_paramIndex) {
                            this._back_meta[p_currentIndex * HeapTimeTreeChunk.META_SIZE] = p_paramIndex;
                        };
                        HeapTimeTreeChunk.prototype.right = function (p_currentIndex) {
                            if (p_currentIndex == -1) {
                                return -1;
                            }
                            return this._back_meta[(p_currentIndex * HeapTimeTreeChunk.META_SIZE) + 1];
                        };
                        HeapTimeTreeChunk.prototype.setRight = function (p_currentIndex, p_paramIndex) {
                            this._back_meta[(p_currentIndex * HeapTimeTreeChunk.META_SIZE) + 1] = p_paramIndex;
                        };
                        HeapTimeTreeChunk.prototype.parent = function (p_currentIndex) {
                            if (p_currentIndex == -1) {
                                return -1;
                            }
                            return this._back_meta[(p_currentIndex * HeapTimeTreeChunk.META_SIZE) + 2];
                        };
                        HeapTimeTreeChunk.prototype.setParent = function (p_currentIndex, p_paramIndex) {
                            this._back_meta[(p_currentIndex * HeapTimeTreeChunk.META_SIZE) + 2] = p_paramIndex;
                        };
                        HeapTimeTreeChunk.prototype.color = function (p_currentIndex) {
                            if (p_currentIndex == -1) {
                                return true;
                            }
                            return this._colors[p_currentIndex];
                        };
                        HeapTimeTreeChunk.prototype.setColor = function (p_currentIndex, p_paramIndex) {
                            this._colors[p_currentIndex] = p_paramIndex;
                        };
                        HeapTimeTreeChunk.prototype.grandParent = function (p_currentIndex) {
                            if (p_currentIndex == -1) {
                                return -1;
                            }
                            if (this.parent(p_currentIndex) != -1) {
                                return this.parent(this.parent(p_currentIndex));
                            }
                            else {
                                return -1;
                            }
                        };
                        HeapTimeTreeChunk.prototype.sibling = function (p_currentIndex) {
                            if (this.parent(p_currentIndex) == -1) {
                                return -1;
                            }
                            else {
                                if (p_currentIndex == this.left(this.parent(p_currentIndex))) {
                                    return this.right(this.parent(p_currentIndex));
                                }
                                else {
                                    return this.left(this.parent(p_currentIndex));
                                }
                            }
                        };
                        HeapTimeTreeChunk.prototype.uncle = function (p_currentIndex) {
                            if (this.parent(p_currentIndex) != -1) {
                                return this.sibling(this.parent(p_currentIndex));
                            }
                            else {
                                return -1;
                            }
                        };
                        HeapTimeTreeChunk.prototype.previous = function (p_index) {
                            var p = p_index;
                            if (this.left(p) != -1) {
                                p = this.left(p);
                                while (this.right(p) != -1) {
                                    p = this.right(p);
                                }
                                return p;
                            }
                            else {
                                if (this.parent(p) != -1) {
                                    if (p == this.right(this.parent(p))) {
                                        return this.parent(p);
                                    }
                                    else {
                                        while (this.parent(p) != -1 && p == this.left(this.parent(p))) {
                                            p = this.parent(p);
                                        }
                                        return this.parent(p);
                                    }
                                }
                                else {
                                    return -1;
                                }
                            }
                        };
                        HeapTimeTreeChunk.prototype.internal_previousOrEqual_index = function (p_key) {
                            var p = this._root;
                            if (p == -1) {
                                return p;
                            }
                            while (p != -1) {
                                if (p_key == this.key(p)) {
                                    return p;
                                }
                                if (p_key > this.key(p)) {
                                    if (this.right(p) != -1) {
                                        p = this.right(p);
                                    }
                                    else {
                                        return p;
                                    }
                                }
                                else {
                                    if (this.left(p) != -1) {
                                        p = this.left(p);
                                    }
                                    else {
                                        var parent_1 = this.parent(p);
                                        var ch = p;
                                        while (parent_1 != -1 && ch == this.left(parent_1)) {
                                            ch = parent_1;
                                            parent_1 = this.parent(parent_1);
                                        }
                                        return parent_1;
                                    }
                                }
                            }
                            return -1;
                        };
                        HeapTimeTreeChunk.prototype.rotateLeft = function (n) {
                            var r = this.right(n);
                            this.replaceNode(n, r);
                            this.setRight(n, this.left(r));
                            if (this.left(r) != -1) {
                                this.setParent(this.left(r), n);
                            }
                            this.setLeft(r, n);
                            this.setParent(n, r);
                        };
                        HeapTimeTreeChunk.prototype.rotateRight = function (n) {
                            var l = this.left(n);
                            this.replaceNode(n, l);
                            this.setLeft(n, this.right(l));
                            if (this.right(l) != -1) {
                                this.setParent(this.right(l), n);
                            }
                            this.setRight(l, n);
                            this.setParent(n, l);
                        };
                        HeapTimeTreeChunk.prototype.replaceNode = function (oldn, newn) {
                            if (this.parent(oldn) == -1) {
                                this._root = newn;
                            }
                            else {
                                if (oldn == this.left(this.parent(oldn))) {
                                    this.setLeft(this.parent(oldn), newn);
                                }
                                else {
                                    this.setRight(this.parent(oldn), newn);
                                }
                            }
                            if (newn != -1) {
                                this.setParent(newn, this.parent(oldn));
                            }
                        };
                        HeapTimeTreeChunk.prototype.insertCase1 = function (n) {
                            if (this.parent(n) == -1) {
                                this.setColor(n, true);
                            }
                            else {
                                this.insertCase2(n);
                            }
                        };
                        HeapTimeTreeChunk.prototype.insertCase2 = function (n) {
                            if (!this.color(this.parent(n))) {
                                this.insertCase3(n);
                            }
                        };
                        HeapTimeTreeChunk.prototype.insertCase3 = function (n) {
                            if (!this.color(this.uncle(n))) {
                                this.setColor(this.parent(n), true);
                                this.setColor(this.uncle(n), true);
                                this.setColor(this.grandParent(n), false);
                                this.insertCase1(this.grandParent(n));
                            }
                            else {
                                this.insertCase4(n);
                            }
                        };
                        HeapTimeTreeChunk.prototype.insertCase4 = function (n_n) {
                            var n = n_n;
                            if (n == this.right(this.parent(n)) && this.parent(n) == this.left(this.grandParent(n))) {
                                this.rotateLeft(this.parent(n));
                                n = this.left(n);
                            }
                            else {
                                if (n == this.left(this.parent(n)) && this.parent(n) == this.right(this.grandParent(n))) {
                                    this.rotateRight(this.parent(n));
                                    n = this.right(n);
                                }
                            }
                            this.insertCase5(n);
                        };
                        HeapTimeTreeChunk.prototype.insertCase5 = function (n) {
                            this.setColor(this.parent(n), true);
                            this.setColor(this.grandParent(n), false);
                            if (n == this.left(this.parent(n)) && this.parent(n) == this.left(this.grandParent(n))) {
                                this.rotateRight(this.grandParent(n));
                            }
                            else {
                                this.rotateLeft(this.grandParent(n));
                            }
                        };
                        HeapTimeTreeChunk.prototype.internal_insert = function (p_key) {
                            if (this._k == null || this._k.length == this._size) {
                                var length_1 = this._size;
                                if (length_1 == 0) {
                                    length_1 = org.mwg.Constants.MAP_INITIAL_CAPACITY;
                                }
                                else {
                                    length_1 = length_1 * 2;
                                }
                                this.reallocate(length_1);
                            }
                            var newIndex = this._size;
                            if (newIndex == 0) {
                                this.setKey(newIndex, p_key);
                                this.setColor(newIndex, false);
                                this.setLeft(newIndex, -1);
                                this.setRight(newIndex, -1);
                                this.setParent(newIndex, -1);
                                this._root = newIndex;
                                this._size = 1;
                            }
                            else {
                                var n = this._root;
                                while (true) {
                                    if (p_key == this.key(n)) {
                                        return false;
                                    }
                                    else if (p_key < this.key(n)) {
                                        if (this.left(n) == -1) {
                                            this.setKey(newIndex, p_key);
                                            this.setColor(newIndex, false);
                                            this.setLeft(newIndex, -1);
                                            this.setRight(newIndex, -1);
                                            this.setParent(newIndex, -1);
                                            this.setLeft(n, newIndex);
                                            this._size++;
                                            break;
                                        }
                                        else {
                                            n = this.left(n);
                                        }
                                    }
                                    else {
                                        if (this.right(n) == -1) {
                                            this.setKey(newIndex, p_key);
                                            this.setColor(newIndex, false);
                                            this.setLeft(newIndex, -1);
                                            this.setRight(newIndex, -1);
                                            this.setParent(newIndex, -1);
                                            this.setRight(n, newIndex);
                                            this._size++;
                                            break;
                                        }
                                        else {
                                            n = this.right(n);
                                        }
                                    }
                                }
                                this.setParent(newIndex, n);
                            }
                            this.insertCase1(newIndex);
                            return true;
                        };
                        HeapTimeTreeChunk.prototype.internal_set_dirty = function () {
                            this._magic = this._magic + 1;
                            if (this._space != null && !this._dirty) {
                                this._dirty = true;
                                this._space.notifyUpdate(this._index);
                            }
                        };
                        HeapTimeTreeChunk.META_SIZE = 3;
                        return HeapTimeTreeChunk;
                    }());
                    heap.HeapTimeTreeChunk = HeapTimeTreeChunk;
                    var HeapWorldOrderChunk = (function () {
                        function HeapWorldOrderChunk(p_space, p_index) {
                            this._index = p_index;
                            this._space = p_space;
                            this._lock = 0;
                            this._magic = 0;
                            this._extra = org.mwg.core.CoreConstants.NULL_LONG;
                            this._size = 0;
                            this._capacity = 0;
                            this._kv = null;
                            this._next = null;
                            this._hash = null;
                            this._dirty = false;
                        }
                        HeapWorldOrderChunk.prototype.world = function () {
                            return this._space.worldByIndex(this._index);
                        };
                        HeapWorldOrderChunk.prototype.time = function () {
                            return this._space.timeByIndex(this._index);
                        };
                        HeapWorldOrderChunk.prototype.id = function () {
                            return this._space.idByIndex(this._index);
                        };
                        HeapWorldOrderChunk.prototype.extra = function () {
                            return this._extra;
                        };
                        HeapWorldOrderChunk.prototype.setExtra = function (extraValue) {
                            this._extra = extraValue;
                        };
                        HeapWorldOrderChunk.prototype.lock = function () {
                        };
                        HeapWorldOrderChunk.prototype.unlock = function () {
                        };
                        HeapWorldOrderChunk.prototype.magic = function () {
                            return this._magic;
                        };
                        HeapWorldOrderChunk.prototype.each = function (callback) {
                            for (var i = 0; i < this._size; i++) {
                                callback(this._kv[i * 2], this._kv[i * 2 + 1]);
                            }
                        };
                        HeapWorldOrderChunk.prototype.get = function (key) {
                            if (this._size > 0) {
                                var index = org.mwg.utility.HashHelper.longHash(key, this._capacity * 2);
                                var m = this._hash[index];
                                while (m >= 0) {
                                    if (key == this._kv[m * 2]) {
                                        return this._kv[(m * 2) + 1];
                                    }
                                    else {
                                        m = this._next[m];
                                    }
                                }
                            }
                            return org.mwg.core.CoreConstants.NULL_LONG;
                        };
                        HeapWorldOrderChunk.prototype.put = function (key, value) {
                            this.internal_put(key, value, true);
                        };
                        HeapWorldOrderChunk.prototype.internal_put = function (key, value, notifyUpdate) {
                            if (this._capacity > 0) {
                                var hashIndex = org.mwg.utility.HashHelper.longHash(key, this._capacity * 2);
                                var m = this._hash[hashIndex];
                                var found = -1;
                                while (m >= 0) {
                                    if (key == this._kv[m * 2]) {
                                        found = m;
                                        break;
                                    }
                                    m = this._next[m];
                                }
                                if (found == -1) {
                                    if (this._capacity == this._size) {
                                        this.resize(this._capacity * 2);
                                        hashIndex = org.mwg.utility.HashHelper.longHash(key, this._capacity * 2);
                                    }
                                    this._kv[this._size * 2] = key;
                                    this._kv[this._size * 2 + 1] = value;
                                    this._next[this._size] = this._hash[hashIndex];
                                    this._hash[hashIndex] = this._size;
                                    this._size++;
                                    this._magic = this._magic + 1;
                                    if (notifyUpdate && !this._dirty) {
                                        this._dirty = true;
                                        if (this._space != null) {
                                            this._space.notifyUpdate(this._index);
                                        }
                                    }
                                }
                                else {
                                    if (this._kv[found * 2 + 1] != value) {
                                        this._kv[found * 2 + 1] = value;
                                        this._magic = this._magic + 1;
                                        if (notifyUpdate && !this._dirty) {
                                            this._dirty = true;
                                            if (this._space != null) {
                                                this._space.notifyUpdate(this._index);
                                            }
                                        }
                                    }
                                }
                            }
                            else {
                                this._capacity = org.mwg.Constants.MAP_INITIAL_CAPACITY;
                                this._next = new Int32Array(this._capacity);
                                java.util.Arrays.fill(this._next, 0, this._capacity, -1);
                                this._hash = new Int32Array(this._capacity * 2);
                                java.util.Arrays.fill(this._hash, 0, this._capacity * 2, -1);
                                this._kv = new Float64Array(this._capacity * 2);
                                this._size = 1;
                                this._kv[0] = key;
                                this._kv[1] = value;
                                this._hash[org.mwg.utility.HashHelper.longHash(key, this._capacity * 2)] = 0;
                                if (notifyUpdate && !this._dirty) {
                                    this._dirty = true;
                                    if (this._space != null) {
                                        this._space.notifyUpdate(this._index);
                                    }
                                }
                            }
                        };
                        HeapWorldOrderChunk.prototype.resize = function (newCapacity) {
                            if (newCapacity > this._capacity) {
                                if (this._kv == null) {
                                    this._kv = new Float64Array(newCapacity * 2);
                                    this._hash = new Int32Array(newCapacity * 2);
                                    this._next = new Int32Array(newCapacity);
                                    this._capacity = newCapacity;
                                    java.util.Arrays.fill(this._next, 0, newCapacity, -1);
                                    java.util.Arrays.fill(this._hash, 0, newCapacity * 2, -1);
                                    return true;
                                }
                                else {
                                    var temp_kv = new Float64Array(newCapacity * 2);
                                    java.lang.System.arraycopy(this._kv, 0, temp_kv, 0, this._size * 2);
                                    var temp_next = new Int32Array(newCapacity);
                                    var temp_hash = new Int32Array(newCapacity * 2);
                                    java.util.Arrays.fill(temp_next, 0, newCapacity, -1);
                                    java.util.Arrays.fill(temp_hash, 0, newCapacity * 2, -1);
                                    for (var i = 0; i < this._size; i++) {
                                        var loopIndex = org.mwg.utility.HashHelper.longHash(temp_kv[i * 2], newCapacity * 2);
                                        temp_next[i] = temp_hash[loopIndex];
                                        temp_hash[loopIndex] = i;
                                    }
                                    this._capacity = newCapacity;
                                    this._hash = temp_hash;
                                    this._next = temp_next;
                                    this._kv = temp_kv;
                                    return true;
                                }
                            }
                            else {
                                return false;
                            }
                        };
                        HeapWorldOrderChunk.prototype.load = function (buffer) {
                            if (buffer == null || buffer.length() == 0) {
                                return;
                            }
                            var isInitial = this._kv == null;
                            var cursor = 0;
                            var bufferSize = buffer.length();
                            var initDone = false;
                            var previousStart = 0;
                            var loopKey = org.mwg.core.CoreConstants.NULL_LONG;
                            while (cursor < bufferSize) {
                                if (buffer.read(cursor) == org.mwg.core.CoreConstants.CHUNK_SEP) {
                                    if (!initDone) {
                                        this.resize(org.mwg.utility.Base64.decodeToLongWithBounds(buffer, 0, cursor));
                                        initDone = true;
                                    }
                                    else {
                                        this._extra = org.mwg.utility.Base64.decodeToLongWithBounds(buffer, previousStart, cursor);
                                    }
                                    previousStart = cursor + 1;
                                }
                                else if (buffer.read(cursor) == org.mwg.core.CoreConstants.CHUNK_SUB_SEP) {
                                    if (loopKey != org.mwg.core.CoreConstants.NULL_LONG) {
                                        var loopValue = org.mwg.utility.Base64.decodeToLongWithBounds(buffer, previousStart, cursor);
                                        this.internal_put(loopKey, loopValue, !isInitial);
                                        loopKey = org.mwg.core.CoreConstants.NULL_LONG;
                                    }
                                    previousStart = cursor + 1;
                                }
                                else if (buffer.read(cursor) == org.mwg.core.CoreConstants.CHUNK_SUB_SUB_SEP) {
                                    loopKey = org.mwg.utility.Base64.decodeToLongWithBounds(buffer, previousStart, cursor);
                                    previousStart = cursor + 1;
                                }
                                cursor++;
                            }
                            if (loopKey != org.mwg.core.CoreConstants.NULL_LONG) {
                                var loopValue = org.mwg.utility.Base64.decodeToLongWithBounds(buffer, previousStart, cursor);
                                this.internal_put(loopKey, loopValue, !isInitial);
                            }
                        };
                        HeapWorldOrderChunk.prototype.index = function () {
                            return this._index;
                        };
                        HeapWorldOrderChunk.prototype.remove = function (key) {
                            throw new Error("Not implemented yet!!!");
                        };
                        HeapWorldOrderChunk.prototype.size = function () {
                            return this._size;
                        };
                        HeapWorldOrderChunk.prototype.chunkType = function () {
                            return org.mwg.chunk.ChunkType.WORLD_ORDER_CHUNK;
                        };
                        HeapWorldOrderChunk.prototype.save = function (buffer) {
                            org.mwg.utility.Base64.encodeLongToBuffer(this._size, buffer);
                            buffer.write(org.mwg.core.CoreConstants.CHUNK_SEP);
                            if (this._extra != org.mwg.core.CoreConstants.NULL_LONG) {
                                org.mwg.utility.Base64.encodeLongToBuffer(this._extra, buffer);
                                buffer.write(org.mwg.core.CoreConstants.CHUNK_SEP);
                            }
                            var isFirst = true;
                            for (var i = 0; i < this._size; i++) {
                                if (!isFirst) {
                                    buffer.write(org.mwg.core.CoreConstants.CHUNK_SUB_SEP);
                                }
                                isFirst = false;
                                org.mwg.utility.Base64.encodeLongToBuffer(this._kv[i * 2], buffer);
                                buffer.write(org.mwg.core.CoreConstants.CHUNK_SUB_SUB_SEP);
                                org.mwg.utility.Base64.encodeLongToBuffer(this._kv[i * 2 + 1], buffer);
                            }
                            this._dirty = false;
                        };
                        return HeapWorldOrderChunk;
                    }());
                    heap.HeapWorldOrderChunk = HeapWorldOrderChunk;
                })(heap = chunk_2.heap || (chunk_2.heap = {}));
            })(chunk = core.chunk || (core.chunk = {}));
            var memory;
            (function (memory) {
                var HeapBuffer = (function () {
                    function HeapBuffer() {
                    }
                    HeapBuffer.prototype.slice = function (initPos, endPos) {
                        var newSize = (endPos - initPos + 1);
                        var newResult = new Int8Array(newSize);
                        java.lang.System.arraycopy(this.buffer, initPos, newResult, 0, newSize);
                        return newResult;
                    };
                    HeapBuffer.prototype.write = function (b) {
                        if (this.buffer == null) {
                            this.buffer = new Int8Array(org.mwg.core.CoreConstants.MAP_INITIAL_CAPACITY);
                            this.buffer[0] = b;
                            this.writeCursor = 1;
                        }
                        else if (this.writeCursor == this.buffer.length) {
                            var temp = new Int8Array(this.buffer.length * 2);
                            java.lang.System.arraycopy(this.buffer, 0, temp, 0, this.buffer.length);
                            temp[this.writeCursor] = b;
                            this.writeCursor++;
                            this.buffer = temp;
                        }
                        else {
                            this.buffer[this.writeCursor] = b;
                            this.writeCursor++;
                        }
                    };
                    HeapBuffer.prototype.getNewSize = function (old, target) {
                        while (old < target) {
                            old = old * 2;
                        }
                        return old;
                    };
                    HeapBuffer.prototype.writeAll = function (bytes) {
                        if (this.buffer == null) {
                            var initSize = this.getNewSize(org.mwg.core.CoreConstants.MAP_INITIAL_CAPACITY, bytes.length);
                            this.buffer = new Int8Array(initSize);
                            java.lang.System.arraycopy(bytes, 0, this.buffer, 0, bytes.length);
                            this.writeCursor = bytes.length;
                        }
                        else if (this.writeCursor + bytes.length > this.buffer.length) {
                            var newSize = this.getNewSize(this.buffer.length, this.buffer.length + bytes.length);
                            var tmp = new Int8Array(newSize);
                            java.lang.System.arraycopy(this.buffer, 0, tmp, 0, this.buffer.length);
                            java.lang.System.arraycopy(bytes, 0, tmp, this.writeCursor, bytes.length);
                            this.buffer = tmp;
                            this.writeCursor = this.writeCursor + bytes.length;
                        }
                        else {
                            java.lang.System.arraycopy(bytes, 0, this.buffer, this.writeCursor, bytes.length);
                            this.writeCursor = this.writeCursor + bytes.length;
                        }
                    };
                    HeapBuffer.prototype.read = function (position) {
                        return this.buffer[position];
                    };
                    HeapBuffer.prototype.data = function () {
                        var copy = new Int8Array(this.writeCursor);
                        if (this.buffer != null) {
                            java.lang.System.arraycopy(this.buffer, 0, copy, 0, this.writeCursor);
                        }
                        return copy;
                    };
                    HeapBuffer.prototype.length = function () {
                        return this.writeCursor;
                    };
                    HeapBuffer.prototype.free = function () {
                        this.buffer = null;
                    };
                    HeapBuffer.prototype.iterator = function () {
                        return new org.mwg.utility.DefaultBufferIterator(this);
                    };
                    HeapBuffer.prototype.removeLast = function () {
                        this.writeCursor--;
                    };
                    HeapBuffer.prototype.toString = function () {
                        return String.fromCharCode.apply(null, this.data());
                    };
                    return HeapBuffer;
                }());
                memory.HeapBuffer = HeapBuffer;
                var HeapMemoryFactory = (function () {
                    function HeapMemoryFactory() {
                    }
                    HeapMemoryFactory.prototype.newSpace = function (memorySize, graph) {
                        return new org.mwg.core.chunk.heap.HeapChunkSpace(memorySize, graph);
                    };
                    HeapMemoryFactory.prototype.newBuffer = function () {
                        return new org.mwg.core.memory.HeapBuffer();
                    };
                    return HeapMemoryFactory;
                }());
                memory.HeapMemoryFactory = HeapMemoryFactory;
            })(memory = core.memory || (core.memory = {}));
            var scheduler;
            (function (scheduler) {
                var JobQueue = (function () {
                    function JobQueue() {
                        this.first = null;
                        this.last = null;
                    }
                    JobQueue.prototype.add = function (item) {
                        var elem = new org.mwg.core.scheduler.JobQueue.JobQueueElem(item, null);
                        if (this.first == null) {
                            this.first = elem;
                            this.last = elem;
                        }
                        else {
                            this.last._next = elem;
                            this.last = elem;
                        }
                    };
                    JobQueue.prototype.poll = function () {
                        var value = this.first;
                        this.first = this.first._next;
                        return value._ptr;
                    };
                    return JobQueue;
                }());
                scheduler.JobQueue = JobQueue;
                var JobQueue;
                (function (JobQueue) {
                    var JobQueueElem = (function () {
                        function JobQueueElem(ptr, next) {
                            this._ptr = ptr;
                            this._next = next;
                        }
                        return JobQueueElem;
                    }());
                    JobQueue.JobQueueElem = JobQueueElem;
                })(JobQueue = scheduler.JobQueue || (scheduler.JobQueue = {}));
                var NoopScheduler = (function () {
                    function NoopScheduler() {
                    }
                    NoopScheduler.prototype.dispatch = function (affinity, job) {
                        job();
                    };
                    NoopScheduler.prototype.start = function () { };
                    NoopScheduler.prototype.stop = function () { };
                    NoopScheduler.prototype.workers = function () {
                        return 1;
                    };
                    return NoopScheduler;
                }());
                scheduler.NoopScheduler = NoopScheduler;
                var TrampolineScheduler = (function () {
                    function TrampolineScheduler() {
                        this.queue = new org.mwg.core.scheduler.JobQueue();
                        this.wip = new java.util.concurrent.atomic.AtomicInteger(0);
                    }
                    TrampolineScheduler.prototype.dispatch = function (affinity, job) {
                        this.queue.add(job);
                        if (this.wip.getAndIncrement() == 0) {
                            do {
                                var polled = this.queue.poll();
                                if (polled != null) {
                                    polled();
                                }
                            } while (this.wip.decrementAndGet() > 0);
                        }
                    };
                    TrampolineScheduler.prototype.start = function () { };
                    TrampolineScheduler.prototype.stop = function () { };
                    TrampolineScheduler.prototype.workers = function () {
                        return 1;
                    };
                    return TrampolineScheduler;
                }());
                scheduler.TrampolineScheduler = TrampolineScheduler;
            })(scheduler = core.scheduler || (core.scheduler = {}));
            var task;
            (function (task) {
                var ActionAdd = (function (_super) {
                    __extends(ActionAdd, _super);
                    function ActionAdd(relationName, variableNameToAdd) {
                        _super.call(this);
                        this._relationName = relationName;
                        this._variableNameToAdd = variableNameToAdd;
                    }
                    ActionAdd.prototype.eval = function (context) {
                        var previousResult = context.result();
                        var savedVar = context.variable(context.template(this._variableNameToAdd));
                        if (previousResult != null && savedVar != null) {
                            var relName = context.template(this._relationName);
                            var previousResultIt = previousResult.iterator();
                            var iter = previousResultIt.next();
                            while (iter != null) {
                                if (iter instanceof org.mwg.plugin.AbstractNode) {
                                    var savedVarIt = savedVar.iterator();
                                    var toAddIter = savedVarIt.next();
                                    while (toAddIter != null) {
                                        if (toAddIter instanceof org.mwg.plugin.AbstractNode) {
                                            iter.add(relName, toAddIter);
                                        }
                                        toAddIter = savedVarIt.next();
                                    }
                                }
                                iter = previousResultIt.next();
                            }
                        }
                        context.continueTask();
                    };
                    ActionAdd.prototype.toString = function () {
                        return "add(\'" + this._relationName + "\'" + org.mwg.Constants.QUERY_SEP + "\'" + this._variableNameToAdd + "\')";
                    };
                    return ActionAdd;
                }(org.mwg.plugin.AbstractTaskAction));
                task.ActionAdd = ActionAdd;
                var ActionAddTo = (function (_super) {
                    __extends(ActionAddTo, _super);
                    function ActionAddTo(relationName, variableNameTarget) {
                        _super.call(this);
                        this._relationName = relationName;
                        this._variableNameTarget = variableNameTarget;
                    }
                    ActionAddTo.prototype.eval = function (context) {
                        var previousResult = context.result();
                        var savedVar = context.variable(context.template(this._variableNameTarget));
                        if (previousResult != null && savedVar != null) {
                            var relName = context.template(this._relationName);
                            var previousResultIt = previousResult.iterator();
                            var iter = previousResultIt.next();
                            while (iter != null) {
                                if (iter instanceof org.mwg.plugin.AbstractNode) {
                                    var savedVarIt = savedVar.iterator();
                                    var toAddIter = savedVarIt.next();
                                    while (toAddIter != null) {
                                        if (toAddIter instanceof org.mwg.plugin.AbstractNode) {
                                            toAddIter.add(relName, iter);
                                        }
                                        toAddIter = savedVarIt.next();
                                    }
                                }
                                iter = previousResultIt.next();
                            }
                        }
                        context.continueTask();
                    };
                    ActionAddTo.prototype.toString = function () {
                        return "addTo(\'" + this._relationName + "\'" + org.mwg.Constants.QUERY_SEP + "\'" + this._variableNameTarget + "\')";
                    };
                    return ActionAddTo;
                }(org.mwg.plugin.AbstractTaskAction));
                task.ActionAddTo = ActionAddTo;
                var ActionAddToVar = (function (_super) {
                    __extends(ActionAddToVar, _super);
                    function ActionAddToVar(p_name, p_global) {
                        _super.call(this);
                        this._name = p_name;
                        this._global = p_global;
                    }
                    ActionAddToVar.prototype.eval = function (context) {
                        var previousResult = context.result();
                        if (this._global) {
                            context.addToGlobalVariable(context.template(this._name), previousResult);
                        }
                        else {
                            context.addToVariable(context.template(this._name), previousResult);
                        }
                        context.continueTask();
                    };
                    ActionAddToVar.prototype.toString = function () {
                        if (this._global) {
                            return "addToGlobalVar(\'" + this._name + "\')";
                        }
                        else {
                            return "addToVar(\'" + this._name + "\')";
                        }
                    };
                    return ActionAddToVar;
                }(org.mwg.plugin.AbstractTaskAction));
                task.ActionAddToVar = ActionAddToVar;
                var ActionAsVar = (function (_super) {
                    __extends(ActionAsVar, _super);
                    function ActionAsVar(p_name, p_global) {
                        _super.call(this);
                        this._name = p_name;
                        this._global = p_global;
                    }
                    ActionAsVar.prototype.eval = function (context) {
                        var previousResult = context.result();
                        if (this._global) {
                            context.setGlobalVariable(context.template(this._name), previousResult);
                        }
                        else {
                            context.setVariable(context.template(this._name), previousResult);
                        }
                        context.continueTask();
                    };
                    ActionAsVar.prototype.toString = function () {
                        if (this._global) {
                            return "asGlobalVar(\'" + this._name + "\')";
                        }
                        else {
                            return "asVar(\'" + this._name + "\')";
                        }
                    };
                    return ActionAsVar;
                }(org.mwg.plugin.AbstractTaskAction));
                task.ActionAsVar = ActionAsVar;
                var ActionClear = (function (_super) {
                    __extends(ActionClear, _super);
                    function ActionClear() {
                        _super.apply(this, arguments);
                    }
                    ActionClear.prototype.eval = function (context) {
                        context.continueWith(context.newResult());
                    };
                    ActionClear.prototype.toString = function () {
                        return "clear()";
                    };
                    return ActionClear;
                }(org.mwg.plugin.AbstractTaskAction));
                task.ActionClear = ActionClear;
                var ActionDefineVar = (function (_super) {
                    __extends(ActionDefineVar, _super);
                    function ActionDefineVar(p_name) {
                        _super.call(this);
                        this._name = p_name;
                    }
                    ActionDefineVar.prototype.eval = function (context) {
                        var previousResult = context.result();
                        context.defineVariable(context.template(this._name), previousResult);
                        context.continueTask();
                    };
                    ActionDefineVar.prototype.toString = function () {
                        return "defineVar(\'" + this._name + "\')";
                    };
                    return ActionDefineVar;
                }(org.mwg.plugin.AbstractTaskAction));
                task.ActionDefineVar = ActionDefineVar;
                var ActionDoWhile = (function (_super) {
                    __extends(ActionDoWhile, _super);
                    function ActionDoWhile(p_then, p_cond) {
                        _super.call(this);
                        this._cond = p_cond;
                        this._then = p_then;
                    }
                    ActionDoWhile.prototype.eval = function (context) {
                        var _this = this;
                        var coreTaskContext = context;
                        var selfPointer = this;
                        var recursiveAction = new Array(1);
                        recursiveAction[0] = function (res) {
                            {
                                var previous = coreTaskContext._result;
                                coreTaskContext._result = res;
                                if (_this._cond(context)) {
                                    if (previous != null) {
                                        previous.free();
                                    }
                                    selfPointer._then.executeFrom(context, context._result, org.mwg.plugin.SchedulerAffinity.SAME_THREAD, recursiveAction[0]);
                                }
                                else {
                                    if (previous != null) {
                                        previous.free();
                                    }
                                    context.continueWith(res);
                                }
                            }
                        };
                        this._then.executeFrom(context, coreTaskContext._result, org.mwg.plugin.SchedulerAffinity.SAME_THREAD, recursiveAction[0]);
                    };
                    ActionDoWhile.prototype.toString = function () {
                        return "doWhile()";
                    };
                    return ActionDoWhile;
                }(org.mwg.plugin.AbstractTaskAction));
                task.ActionDoWhile = ActionDoWhile;
                var ActionFlatmap = (function (_super) {
                    __extends(ActionFlatmap, _super);
                    function ActionFlatmap(p_subTask) {
                        _super.call(this);
                        this._subTask = p_subTask;
                    }
                    ActionFlatmap.prototype.eval = function (context) {
                        var _this = this;
                        var selfPointer = this;
                        var previousResult = context.result();
                        if (previousResult == null) {
                            context.continueTask();
                        }
                        else {
                            var it_1 = previousResult.iterator();
                            var finalResult_1 = context.newResult();
                            finalResult_1.allocate(previousResult.size());
                            var recursiveAction_1 = new Array(1);
                            var loopRes_1 = new Array(1);
                            recursiveAction_1[0] = function (res) {
                                {
                                    if (res != null) {
                                        for (var i = 0; i < res.size(); i++) {
                                            finalResult_1.add(res.get(i));
                                        }
                                    }
                                    loopRes_1[0].free();
                                    var nextResult_1 = it_1.nextWithIndex();
                                    if (nextResult_1 != null) {
                                        loopRes_1[0] = context.wrap(nextResult_1.right());
                                    }
                                    else {
                                        loopRes_1[0] = null;
                                    }
                                    if (nextResult_1 == null) {
                                        context.continueWith(finalResult_1);
                                    }
                                    else {
                                        selfPointer._subTask.executeFromUsing(context, loopRes_1[0], org.mwg.plugin.SchedulerAffinity.SAME_THREAD, function (result) {
                                            {
                                                result.defineVariable("i", nextResult_1.left());
                                            }
                                        }, recursiveAction_1[0]);
                                    }
                                }
                            };
                            var nextRes_1 = it_1.nextWithIndex();
                            if (nextRes_1 != null) {
                                loopRes_1[0] = context.wrap(nextRes_1.right());
                                context.graph().scheduler().dispatch(org.mwg.plugin.SchedulerAffinity.SAME_THREAD, function () {
                                    {
                                        _this._subTask.executeFromUsing(context, loopRes_1[0], org.mwg.plugin.SchedulerAffinity.SAME_THREAD, function (result) {
                                            {
                                                result.defineVariable("i", nextRes_1.left());
                                            }
                                        }, recursiveAction_1[0]);
                                    }
                                });
                            }
                            else {
                                context.continueWith(finalResult_1);
                            }
                        }
                    };
                    ActionFlatmap.prototype.toString = function () {
                        return "flatMap()";
                    };
                    return ActionFlatmap;
                }(org.mwg.plugin.AbstractTaskAction));
                task.ActionFlatmap = ActionFlatmap;
                var ActionFlatmapPar = (function (_super) {
                    __extends(ActionFlatmapPar, _super);
                    function ActionFlatmapPar(p_subTask) {
                        _super.call(this);
                        this._subTask = p_subTask;
                    }
                    ActionFlatmapPar.prototype.eval = function (context) {
                        var _this = this;
                        var previousResult = context.result();
                        var finalResult = context.wrap(null);
                        var it = previousResult.iterator();
                        var previousSize = previousResult.size();
                        if (previousSize == -1) {
                            throw new Error("Foreach on non array structure are not supported yet!");
                        }
                        finalResult.allocate(previousSize);
                        var waiter = context.graph().newCounter(previousSize);
                        var dequeueJob = new Array(1);
                        dequeueJob[0] = function () {
                            {
                                var loop_1 = it.nextWithIndex();
                                if (loop_1 != null) {
                                    _this._subTask.executeFromUsing(context, context.wrap(loop_1.right()), org.mwg.plugin.SchedulerAffinity.ANY_LOCAL_THREAD, function (result) {
                                        {
                                            result.defineVariable("i", loop_1.left());
                                        }
                                    }, function (result) {
                                        {
                                            if (result != null) {
                                                for (var i = 0; i < result.size(); i++) {
                                                    finalResult.add(result.get(i));
                                                }
                                            }
                                            waiter.count();
                                            dequeueJob[0]();
                                        }
                                    });
                                }
                            }
                        };
                        var nbThread = context.graph().scheduler().workers();
                        for (var i = 0; i < nbThread; i++) {
                            dequeueJob[0]();
                        }
                        waiter.then(function () {
                            {
                                context.continueWith(finalResult);
                            }
                        });
                    };
                    ActionFlatmapPar.prototype.toString = function () {
                        return "flatMapPar()";
                    };
                    return ActionFlatmapPar;
                }(org.mwg.plugin.AbstractTaskAction));
                task.ActionFlatmapPar = ActionFlatmapPar;
                var ActionForeach = (function (_super) {
                    __extends(ActionForeach, _super);
                    function ActionForeach(p_subTask) {
                        _super.call(this);
                        this._subTask = p_subTask;
                    }
                    ActionForeach.prototype.eval = function (context) {
                        var selfPointer = this;
                        var previousResult = context.result();
                        if (previousResult == null) {
                            context.continueTask();
                        }
                        else {
                            var it_2 = previousResult.iterator();
                            var recursiveAction_2 = new Array(1);
                            recursiveAction_2[0] = function (res) {
                                {
                                    if (res != null) {
                                        res.free();
                                    }
                                    var nextResult_2 = it_2.nextWithIndex();
                                    if (nextResult_2 == null) {
                                        context.continueTask();
                                    }
                                    else {
                                        selfPointer._subTask.executeFromUsing(context, context.wrap(nextResult_2.right()), org.mwg.plugin.SchedulerAffinity.SAME_THREAD, function (result) {
                                            {
                                                result.defineVariable("i", nextResult_2.left());
                                            }
                                        }, recursiveAction_2[0]);
                                    }
                                }
                            };
                            var nextRes_2 = it_2.nextWithIndex();
                            if (nextRes_2 != null) {
                                this._subTask.executeFromUsing(context, context.wrap(nextRes_2.right()), org.mwg.plugin.SchedulerAffinity.SAME_THREAD, function (result) {
                                    {
                                        result.defineVariable("i", nextRes_2.left());
                                    }
                                }, recursiveAction_2[0]);
                            }
                            else {
                                context.continueTask();
                            }
                        }
                    };
                    ActionForeach.prototype.toString = function () {
                        return "foreach()";
                    };
                    return ActionForeach;
                }(org.mwg.plugin.AbstractTaskAction));
                task.ActionForeach = ActionForeach;
                var ActionForeachPar = (function (_super) {
                    __extends(ActionForeachPar, _super);
                    function ActionForeachPar(p_subTask) {
                        _super.call(this);
                        this._subTask = p_subTask;
                    }
                    ActionForeachPar.prototype.eval = function (context) {
                        var _this = this;
                        var previousResult = context.result();
                        var it = previousResult.iterator();
                        var previousSize = previousResult.size();
                        if (previousSize == -1) {
                            throw new Error("Foreach on non array structure are not supported yet!");
                        }
                        var waiter = context.graph().newCounter(previousSize);
                        var dequeueJob = new Array(1);
                        dequeueJob[0] = function () {
                            {
                                var loop_2 = it.nextWithIndex();
                                if (loop_2 != null) {
                                    _this._subTask.executeFromUsing(context, context.wrap(loop_2.right()), org.mwg.plugin.SchedulerAffinity.ANY_LOCAL_THREAD, function (result) {
                                        {
                                            result.defineVariable("i", loop_2.left());
                                        }
                                    }, function (result) {
                                        {
                                            if (result != null) {
                                                result.free();
                                            }
                                            waiter.count();
                                            dequeueJob[0]();
                                        }
                                    });
                                }
                            }
                        };
                        var nbThread = context.graph().scheduler().workers();
                        for (var i = 0; i < nbThread; i++) {
                            dequeueJob[0]();
                        }
                        waiter.then(function () {
                            {
                                context.continueTask();
                            }
                        });
                    };
                    ActionForeachPar.prototype.toString = function () {
                        return "foreachPar()";
                    };
                    return ActionForeachPar;
                }(org.mwg.plugin.AbstractTaskAction));
                task.ActionForeachPar = ActionForeachPar;
                var ActionFromIndex = (function (_super) {
                    __extends(ActionFromIndex, _super);
                    function ActionFromIndex(p_indexName, p_query) {
                        _super.call(this);
                        this._indexName = p_indexName;
                        this._query = p_query;
                    }
                    ActionFromIndex.prototype.eval = function (context) {
                        var flatIndexName = context.template(this._indexName);
                        var flatQuery = context.template(this._query);
                        context.graph().find(context.world(), context.time(), flatIndexName, flatQuery, function (result) {
                            {
                                context.continueWith(context.wrap(result));
                            }
                        });
                    };
                    ActionFromIndex.prototype.toString = function () {
                        return "fromIndex(\'" + this._indexName + "\'" + org.mwg.Constants.QUERY_SEP + "\'" + this._query + "\')";
                    };
                    return ActionFromIndex;
                }(org.mwg.plugin.AbstractTaskAction));
                task.ActionFromIndex = ActionFromIndex;
                var ActionFromIndexAll = (function (_super) {
                    __extends(ActionFromIndexAll, _super);
                    function ActionFromIndexAll(p_indexName) {
                        _super.call(this);
                        this._indexName = p_indexName;
                    }
                    ActionFromIndexAll.prototype.eval = function (context) {
                        var templatedINdexName = context.template(this._indexName);
                        context.graph().findAll(context.world(), context.time(), templatedINdexName, function (result) {
                            {
                                context.continueWith(context.wrap(result));
                            }
                        });
                    };
                    ActionFromIndexAll.prototype.toString = function () {
                        return "fromIndexAll(\'" + this._indexName + "\')";
                    };
                    return ActionFromIndexAll;
                }(org.mwg.plugin.AbstractTaskAction));
                task.ActionFromIndexAll = ActionFromIndexAll;
                var ActionFromVar = (function (_super) {
                    __extends(ActionFromVar, _super);
                    function ActionFromVar(p_name, p_index) {
                        _super.call(this);
                        this._name = p_name;
                        this._index = p_index;
                    }
                    ActionFromVar.prototype.eval = function (context) {
                        var evaluatedName = context.template(this._name);
                        var varResult;
                        if (this._index != -1) {
                            varResult = context.wrap(context.variable(evaluatedName).get(this._index));
                        }
                        else {
                            varResult = context.variable(evaluatedName);
                        }
                        if (varResult != null) {
                            varResult = varResult.clone();
                        }
                        context.continueWith(varResult);
                    };
                    ActionFromVar.prototype.toString = function () {
                        return "fromVar(\'" + this._name + "\')";
                    };
                    return ActionFromVar;
                }(org.mwg.plugin.AbstractTaskAction));
                task.ActionFromVar = ActionFromVar;
                var ActionGet = (function (_super) {
                    __extends(ActionGet, _super);
                    function ActionGet(p_name) {
                        _super.call(this);
                        this._name = p_name;
                    }
                    ActionGet.prototype.eval = function (context) {
                        var finalResult = context.newResult();
                        var flatName = context.template(this._name);
                        var previousResult = context.result();
                        if (previousResult != null) {
                            var previousSize = previousResult.size();
                            var defer_1 = context.graph().newCounter(previousSize);
                            var _loop_4 = function(i) {
                                var loop = previousResult.get(i);
                                if (loop instanceof org.mwg.plugin.AbstractNode) {
                                    var casted_1 = loop;
                                    if (casted_1.type(flatName) == org.mwg.Type.RELATION) {
                                        casted_1.rel(flatName, function (result) {
                                            {
                                                if (result != null) {
                                                    for (var j = 0; j < result.length; j++) {
                                                        finalResult.add(result[j]);
                                                    }
                                                }
                                                casted_1.free();
                                                defer_1.count();
                                            }
                                        });
                                    }
                                    else {
                                        var resolved = casted_1.get(flatName);
                                        if (resolved != null) {
                                            finalResult.add(resolved);
                                        }
                                        casted_1.free();
                                        defer_1.count();
                                    }
                                }
                                else {
                                    finalResult.add(loop);
                                    defer_1.count();
                                }
                            };
                            for (var i = 0; i < previousSize; i++) {
                                _loop_4(i);
                            }
                            defer_1.then(function () {
                                {
                                    previousResult.clear();
                                    context.continueWith(finalResult);
                                }
                            });
                        }
                        else {
                            context.continueTask();
                        }
                    };
                    ActionGet.prototype.toString = function () {
                        return "get(\'" + this._name + "\')";
                    };
                    return ActionGet;
                }(org.mwg.plugin.AbstractTaskAction));
                task.ActionGet = ActionGet;
                var ActionIfThen = (function (_super) {
                    __extends(ActionIfThen, _super);
                    function ActionIfThen(cond, action) {
                        _super.call(this);
                        this._condition = cond;
                        this._action = action;
                    }
                    ActionIfThen.prototype.eval = function (context) {
                        if (this._condition(context)) {
                            this._action.executeFrom(context, context.result(), org.mwg.plugin.SchedulerAffinity.SAME_THREAD, function (res) {
                                {
                                    context.continueWith(res);
                                }
                            });
                        }
                        else {
                            context.continueTask();
                        }
                    };
                    ActionIfThen.prototype.toString = function () {
                        return "ifThen()";
                    };
                    return ActionIfThen;
                }(org.mwg.plugin.AbstractTaskAction));
                task.ActionIfThen = ActionIfThen;
                var ActionIfThenElse = (function (_super) {
                    __extends(ActionIfThenElse, _super);
                    function ActionIfThenElse(cond, p_thenSub, p_elseSub) {
                        _super.call(this);
                        this._condition = cond;
                        this._thenSub = p_thenSub;
                        this._elseSub = p_elseSub;
                    }
                    ActionIfThenElse.prototype.eval = function (context) {
                        if (this._condition(context)) {
                            this._thenSub.executeFrom(context, context.result(), org.mwg.plugin.SchedulerAffinity.SAME_THREAD, function (res) {
                                {
                                    context.continueWith(res);
                                }
                            });
                        }
                        else {
                            this._elseSub.executeFrom(context, context.result(), org.mwg.plugin.SchedulerAffinity.SAME_THREAD, function (res) {
                                {
                                    context.continueWith(res);
                                }
                            });
                        }
                    };
                    ActionIfThenElse.prototype.toString = function () {
                        return "ifThen()";
                    };
                    return ActionIfThenElse;
                }(org.mwg.plugin.AbstractTaskAction));
                task.ActionIfThenElse = ActionIfThenElse;
                var ActionIndexOrUnindexNode = (function (_super) {
                    __extends(ActionIndexOrUnindexNode, _super);
                    function ActionIndexOrUnindexNode(indexName, flatKeyAttributes, isIndexation) {
                        _super.call(this);
                        this._indexName = indexName;
                        this._flatKeyAttributes = flatKeyAttributes;
                        this._isIndexation = isIndexation;
                    }
                    ActionIndexOrUnindexNode.prototype.eval = function (context) {
                        var previousResult = context.result();
                        var templatedIndexName = context.template(this._indexName);
                        var templatedKeyAttributes = context.template(this._flatKeyAttributes);
                        var counter = new org.mwg.core.utility.CoreDeferCounter(previousResult.size());
                        var end = function (succeed) {
                            {
                                if (succeed) {
                                    counter.count();
                                }
                                else {
                                    throw new Error("Error during indexation of node with id " + previousResult.get(0).id());
                                }
                            }
                        };
                        for (var i = 0; i < previousResult.size(); i++) {
                            var loop = previousResult.get(i);
                            if (loop instanceof org.mwg.plugin.AbstractNode) {
                                if (this._isIndexation) {
                                    context.graph().index(templatedIndexName, loop, templatedKeyAttributes, end);
                                }
                                else {
                                    context.graph().unindex(templatedIndexName, loop, templatedKeyAttributes, end);
                                }
                            }
                            else {
                                counter.count();
                            }
                        }
                        counter.then(function () {
                            {
                                context.continueTask();
                            }
                        });
                    };
                    ActionIndexOrUnindexNode.prototype.toString = function () {
                        if (this._isIndexation) {
                            return "indexNode('" + this._indexName + "','" + this._flatKeyAttributes + "')";
                        }
                        else {
                            return "unindexNode('" + this._indexName + "','" + this._flatKeyAttributes + "')";
                        }
                    };
                    return ActionIndexOrUnindexNode;
                }(org.mwg.plugin.AbstractTaskAction));
                task.ActionIndexOrUnindexNode = ActionIndexOrUnindexNode;
                var ActionIndexOrUnindexNodeAt = (function (_super) {
                    __extends(ActionIndexOrUnindexNodeAt, _super);
                    function ActionIndexOrUnindexNodeAt(world, time, indexName, flatKeyAttributes, isIndexation) {
                        _super.call(this);
                        this._indexName = indexName;
                        this._flatKeyAttributes = flatKeyAttributes;
                        this._isIndexation = isIndexation;
                        this._world = world;
                        this._time = time;
                    }
                    ActionIndexOrUnindexNodeAt.prototype.eval = function (context) {
                        var previousResult = context.result();
                        var templatedWorld = java.lang.Long.parseLong(context.template(this._world));
                        var templatedTime = java.lang.Long.parseLong(context.template(this._time));
                        var templatedIndexName = context.template(this._indexName);
                        var templatedKeyAttributes = context.template(this._flatKeyAttributes);
                        var counter = new org.mwg.core.utility.CoreDeferCounter(previousResult.size());
                        var end = function (succeed) {
                            {
                                if (succeed) {
                                    counter.count();
                                }
                                else {
                                    throw new Error("Error during indexation of node with id " + previousResult.get(0).id());
                                }
                            }
                        };
                        for (var i = 0; i < previousResult.size(); i++) {
                            var loop = previousResult.get(i);
                            if (loop instanceof org.mwg.plugin.AbstractNode) {
                                if (this._isIndexation) {
                                    context.graph().indexAt(templatedWorld, templatedTime, templatedIndexName, loop, templatedKeyAttributes, end);
                                }
                                else {
                                    context.graph().unindexAt(templatedWorld, templatedTime, templatedIndexName, loop, templatedKeyAttributes, end);
                                }
                            }
                            else {
                                counter.count();
                            }
                        }
                        counter.then(function () {
                            {
                                context.continueTask();
                            }
                        });
                    };
                    ActionIndexOrUnindexNodeAt.prototype.toString = function () {
                        if (this._isIndexation) {
                            return "indexNodeAt('" + this._world + "','" + this._time + "','" + "'" + this._indexName + "','" + this._flatKeyAttributes + "')";
                        }
                        else {
                            return "unindexNodeAt('" + this._world + "','" + this._time + "','" + "'" + this._indexName + "','" + this._flatKeyAttributes + "')";
                        }
                    };
                    return ActionIndexOrUnindexNodeAt;
                }(org.mwg.plugin.AbstractTaskAction));
                task.ActionIndexOrUnindexNodeAt = ActionIndexOrUnindexNodeAt;
                var ActionIndexesNames = (function (_super) {
                    __extends(ActionIndexesNames, _super);
                    function ActionIndexesNames() {
                        _super.apply(this, arguments);
                    }
                    ActionIndexesNames.prototype.eval = function (context) {
                        context.graph().indexes(context.world(), context.time(), function (result) {
                            {
                                context.continueWith(context.wrap(result));
                            }
                        });
                    };
                    ActionIndexesNames.prototype.toString = function () {
                        return "indexesNames()";
                    };
                    return ActionIndexesNames;
                }(org.mwg.plugin.AbstractTaskAction));
                task.ActionIndexesNames = ActionIndexesNames;
                var ActionInject = (function (_super) {
                    __extends(ActionInject, _super);
                    function ActionInject(value) {
                        _super.call(this);
                        this._value = value;
                    }
                    ActionInject.prototype.eval = function (context) {
                        context.continueWith(context.wrap(this._value).clone());
                    };
                    ActionInject.prototype.toString = function () {
                        return "inject()";
                    };
                    return ActionInject;
                }(org.mwg.plugin.AbstractTaskAction));
                task.ActionInject = ActionInject;
                var ActionIsolate = (function (_super) {
                    __extends(ActionIsolate, _super);
                    function ActionIsolate(p_subTask) {
                        _super.call(this);
                        this._subTask = p_subTask;
                    }
                    ActionIsolate.prototype.eval = function (context) {
                        var previous = context.result();
                        this._subTask.executeFrom(context, previous, org.mwg.plugin.SchedulerAffinity.SAME_THREAD, function (subTaskResult) {
                            {
                                if (subTaskResult != null) {
                                    subTaskResult.free();
                                }
                                context.continueWith(previous);
                            }
                        });
                    };
                    ActionIsolate.prototype.toString = function () {
                        return "subTask()";
                    };
                    return ActionIsolate;
                }(org.mwg.plugin.AbstractTaskAction));
                task.ActionIsolate = ActionIsolate;
                var ActionJump = (function (_super) {
                    __extends(ActionJump, _super);
                    function ActionJump(time) {
                        _super.call(this);
                        this._time = time;
                    }
                    ActionJump.prototype.eval = function (context) {
                        var flatTime = context.template(this._time);
                        var parsedTime;
                        try {
                            parsedTime = java.lang.Long.parseLong(flatTime);
                        }
                        catch ($ex$) {
                            if ($ex$ instanceof Error) {
                                var t = $ex$;
                                {
                                    var d = parseFloat(flatTime);
                                    parsedTime = (d < 0 ? Math.ceil(d) : Math.floor(d));
                                }
                            }
                            else {
                                throw $ex$;
                            }
                        }
                        var previous = context.result();
                        var defer = new org.mwg.core.utility.CoreDeferCounter(previous.size());
                        var previousSize = previous.size();
                        var _loop_5 = function(i) {
                            var loopObj = previous.get(i);
                            if (loopObj instanceof org.mwg.plugin.AbstractNode) {
                                var castedPreviousNode_1 = loopObj;
                                var finalIndex_1 = i;
                                castedPreviousNode_1.jump(parsedTime, function (result) {
                                    {
                                        castedPreviousNode_1.free();
                                        previous.set(finalIndex_1, result);
                                        defer.count();
                                    }
                                });
                            }
                            else {
                                defer.count();
                            }
                        };
                        for (var i = 0; i < previousSize; i++) {
                            _loop_5(i);
                        }
                        defer.then(function () {
                            {
                                context.continueTask();
                            }
                        });
                    };
                    ActionJump.prototype.toString = function () {
                        return "jump(\'" + this._time + "\')";
                    };
                    return ActionJump;
                }(org.mwg.plugin.AbstractTaskAction));
                task.ActionJump = ActionJump;
                var ActionLocalIndexOrUnindex = (function (_super) {
                    __extends(ActionLocalIndexOrUnindex, _super);
                    function ActionLocalIndexOrUnindex(indexedRelation, flatKeyAttributes, varNodeToAdd, _isIndexation) {
                        _super.call(this);
                        this._indexedRelation = indexedRelation;
                        this._flatKeyAttributes = flatKeyAttributes;
                        this._isIndexation = _isIndexation;
                        this._varNodeToAdd = varNodeToAdd;
                    }
                    ActionLocalIndexOrUnindex.prototype.eval = function (context) {
                        var previousResult = context.result();
                        var templatedIndexName = context.template(this._indexedRelation);
                        var templatedKeyAttributes = context.template(this._flatKeyAttributes);
                        var toAdd = context.variable(this._varNodeToAdd);
                        if (toAdd.size() == 0) {
                            throw new Error("Error while adding a new node in a local index: '" + this._varNodeToAdd + "' does not contain any element.");
                        }
                        var counter = new org.mwg.core.utility.CoreDeferCounter(previousResult.size() * toAdd.size());
                        var end = function (succeed) {
                            {
                                if (succeed) {
                                    counter.count();
                                }
                                else {
                                    throw new Error("Error during indexation of node with id " + previousResult.get(0).id());
                                }
                            }
                        };
                        for (var srcNodeIdx = 0; srcNodeIdx < previousResult.size(); srcNodeIdx++) {
                            var srcNode = previousResult.get(srcNodeIdx);
                            for (var targetNodeIdx = 0; targetNodeIdx < toAdd.size(); targetNodeIdx++) {
                                var targetNode = toAdd.get(targetNodeIdx);
                                if (targetNode instanceof org.mwg.plugin.AbstractNode && srcNode instanceof org.mwg.plugin.AbstractNode) {
                                    if (this._isIndexation) {
                                        srcNode.index(templatedIndexName, targetNode, templatedKeyAttributes, end);
                                    }
                                    else {
                                        srcNode.unindex(templatedIndexName, targetNode, templatedKeyAttributes, end);
                                    }
                                }
                                else {
                                    counter.count();
                                }
                            }
                        }
                        counter.then(function () {
                            {
                                context.continueTask();
                            }
                        });
                    };
                    return ActionLocalIndexOrUnindex;
                }(org.mwg.plugin.AbstractTaskAction));
                task.ActionLocalIndexOrUnindex = ActionLocalIndexOrUnindex;
                var ActionLookup = (function (_super) {
                    __extends(ActionLookup, _super);
                    function ActionLookup(p_id) {
                        _super.call(this);
                        this._id = p_id;
                    }
                    ActionLookup.prototype.eval = function (context) {
                        var idL = java.lang.Long.parseLong(context.template(this._id));
                        context.graph().lookup(context.world(), context.time(), idL, function (result) {
                            {
                                context.continueWith(context.wrap(result));
                            }
                        });
                    };
                    ActionLookup.prototype.toString = function () {
                        return "lookup(\'" + this._id + "\")";
                    };
                    return ActionLookup;
                }(org.mwg.plugin.AbstractTaskAction));
                task.ActionLookup = ActionLookup;
                var ActionLookupAll = (function (_super) {
                    __extends(ActionLookupAll, _super);
                    function ActionLookupAll(p_ids) {
                        _super.call(this);
                        this._ids = p_ids;
                    }
                    ActionLookupAll.prototype.eval = function (context) {
                        var afterTemplate = context.template(this._ids).trim();
                        if ((afterTemplate.lastIndexOf("[", 0) === 0)) {
                            afterTemplate = afterTemplate.substring(1, afterTemplate.length - 1);
                        }
                        var values = afterTemplate.split(",");
                        var ids = new Float64Array(values.length);
                        for (var i = 0; i < values.length; i++) {
                            ids[i] = java.lang.Long.parseLong(values[i]);
                        }
                        context.graph().lookupAll(context.world(), context.time(), ids, function (result) {
                            {
                                context.continueWith(context.wrap(result));
                            }
                        });
                    };
                    ActionLookupAll.prototype.toString = function () {
                        return "lookup(\'" + this._ids + "\")";
                    };
                    return ActionLookupAll;
                }(org.mwg.plugin.AbstractTaskAction));
                task.ActionLookupAll = ActionLookupAll;
                var ActionLoop = (function (_super) {
                    __extends(ActionLoop, _super);
                    function ActionLoop(p_lower, p_upper, p_subTask) {
                        _super.call(this);
                        this._subTask = p_subTask;
                        this._lower = p_lower;
                        this._upper = p_upper;
                    }
                    ActionLoop.prototype.eval = function (context) {
                        var lowerString = context.template(this._lower);
                        var upperString = context.template(this._upper);
                        var lower = parseFloat(context.template(lowerString));
                        var upper = parseFloat(context.template(upperString));
                        var previous = context.result();
                        var selfPointer = this;
                        var cursor = new java.util.concurrent.atomic.AtomicInteger(lower);
                        if ((upper - lower) >= 0) {
                            var recursiveAction_3 = new Array(1);
                            recursiveAction_3[0] = function (res) {
                                {
                                    var current_1 = cursor.getAndIncrement();
                                    if (res != null) {
                                        res.free();
                                    }
                                    if (current_1 > upper) {
                                        context.continueTask();
                                    }
                                    else {
                                        selfPointer._subTask.executeFromUsing(context, previous, org.mwg.plugin.SchedulerAffinity.SAME_THREAD, function (result) {
                                            {
                                                result.defineVariable("i", current_1);
                                            }
                                        }, recursiveAction_3[0]);
                                    }
                                }
                            };
                            this._subTask.executeFromUsing(context, previous, org.mwg.plugin.SchedulerAffinity.SAME_THREAD, function (result) {
                                {
                                    result.defineVariable("i", cursor.getAndIncrement());
                                }
                            }, recursiveAction_3[0]);
                        }
                        else {
                            context.continueTask();
                        }
                    };
                    ActionLoop.prototype.toString = function () {
                        return "loop(\'" + this._lower + "\',\'" + this._upper + "\')";
                    };
                    return ActionLoop;
                }(org.mwg.plugin.AbstractTaskAction));
                task.ActionLoop = ActionLoop;
                var ActionLoopPar = (function (_super) {
                    __extends(ActionLoopPar, _super);
                    function ActionLoopPar(p_lower, p_upper, p_subTask) {
                        _super.call(this);
                        this._subTask = p_subTask;
                        this._lower = p_lower;
                        this._upper = p_upper;
                    }
                    ActionLoopPar.prototype.eval = function (context) {
                        var lowerString = context.template(this._lower);
                        var upperString = context.template(this._upper);
                        var lower = parseFloat(context.template(lowerString));
                        var upper = parseFloat(context.template(upperString));
                        var previous = context.result();
                        var next = context.newResult();
                        if ((upper - lower) > 0) {
                            var waiter_1 = context.graph().newCounter((upper - lower) + 1);
                            var _loop_6 = function(i) {
                                var finalI = i;
                                this_2._subTask.executeFromUsing(context, previous, org.mwg.plugin.SchedulerAffinity.ANY_LOCAL_THREAD, function (result) {
                                    {
                                        result.defineVariable("i", finalI);
                                    }
                                }, function (result) {
                                    {
                                        if (result != null && result.size() > 0) {
                                            for (var i_1 = 0; i_1 < result.size(); i_1++) {
                                                next.add(result.get(i_1));
                                            }
                                        }
                                        waiter_1.count();
                                    }
                                });
                            };
                            var this_2 = this;
                            for (var i = lower; i <= upper; i++) {
                                _loop_6(i);
                            }
                            waiter_1.then(function () {
                                {
                                    context.continueWith(next);
                                }
                            });
                        }
                        else {
                            context.continueWith(next);
                        }
                    };
                    ActionLoopPar.prototype.toString = function () {
                        return "loopPar(\'" + this._lower + "\',\'" + this._upper + "\')";
                    };
                    return ActionLoopPar;
                }(org.mwg.plugin.AbstractTaskAction));
                task.ActionLoopPar = ActionLoopPar;
                var ActionMap = (function (_super) {
                    __extends(ActionMap, _super);
                    function ActionMap(p_map) {
                        _super.call(this);
                        this._map = p_map;
                    }
                    ActionMap.prototype.eval = function (context) {
                        var previous = context.result();
                        var next = context.wrap(null);
                        var previousSize = previous.size();
                        for (var i = 0; i < previousSize; i++) {
                            next.add(this._map(previous.get(i)));
                        }
                        context.continueWith(next);
                    };
                    ActionMap.prototype.toString = function () {
                        return "map()";
                    };
                    return ActionMap;
                }(org.mwg.plugin.AbstractTaskAction));
                task.ActionMap = ActionMap;
                var ActionMath = (function (_super) {
                    __extends(ActionMath, _super);
                    function ActionMath(mathExpression) {
                        _super.call(this);
                        this._expression = mathExpression;
                        this._engine = org.mwg.core.task.math.CoreMathExpressionEngine.parse(mathExpression);
                    }
                    ActionMath.prototype.eval = function (context) {
                        var previous = context.result();
                        var next = context.newResult();
                        var previousSize = previous.size();
                        for (var i = 0; i < previousSize; i++) {
                            var loop = previous.get(i);
                            var variables = new java.util.HashMap();
                            variables.put("PI", Math.PI);
                            variables.put("TRUE", 1.0);
                            variables.put("FALSE", 0.0);
                            if (loop instanceof org.mwg.plugin.AbstractNode) {
                                next.add(this._engine.eval(loop, context, variables));
                                loop.free();
                            }
                            else {
                                next.add(this._engine.eval(null, context, variables));
                            }
                        }
                        previous.clear();
                        context.continueWith(next);
                    };
                    ActionMath.prototype.toString = function () {
                        return "math(\'" + this._expression + "\')";
                    };
                    return ActionMath;
                }(org.mwg.plugin.AbstractTaskAction));
                task.ActionMath = ActionMath;
                var ActionNewNode = (function (_super) {
                    __extends(ActionNewNode, _super);
                    function ActionNewNode(typeNode) {
                        _super.call(this);
                        this._typeNode = typeNode;
                    }
                    ActionNewNode.prototype.eval = function (context) {
                        var newNode;
                        if (this._typeNode == null) {
                            newNode = context.graph().newNode(context.world(), context.time());
                        }
                        else {
                            var templatedType = context.template(this._typeNode);
                            newNode = context.graph().newTypedNode(context.world(), context.time(), templatedType);
                        }
                        context.continueWith(context.wrap(newNode));
                    };
                    ActionNewNode.prototype.toString = function () {
                        if (this._typeNode != null) {
                            return "newTypedNode(\'" + this._typeNode + "\')";
                        }
                        else {
                            return "newNode()";
                        }
                    };
                    return ActionNewNode;
                }(org.mwg.plugin.AbstractTaskAction));
                task.ActionNewNode = ActionNewNode;
                var ActionPlugin = (function (_super) {
                    __extends(ActionPlugin, _super);
                    function ActionPlugin(actionName, flatParams) {
                        _super.call(this);
                        this.subAction = null;
                        this._actionName = actionName;
                        this._flatParams = flatParams;
                    }
                    ActionPlugin.prototype.eval = function (context) {
                        var templatedName = context.template(this._actionName);
                        var templatedParams = context.template(this._flatParams);
                        var actionFactory = context.graph().taskAction(templatedName);
                        if (actionFactory == null) {
                            throw new Error("Unknown task action: " + templatedName);
                        }
                        var paramsCapacity = org.mwg.core.CoreConstants.MAP_INITIAL_CAPACITY;
                        var params = new Array(paramsCapacity);
                        var paramsIndex = 0;
                        var cursor = 0;
                        var flatSize = templatedParams.length;
                        var previous = 0;
                        while (cursor < flatSize) {
                            var current = templatedParams.charAt(cursor);
                            if (current == org.mwg.Constants.QUERY_SEP) {
                                var param_1 = templatedParams.substring(previous, cursor);
                                if (param_1.length > 0) {
                                    if (paramsIndex >= paramsCapacity) {
                                        var newParamsCapacity = paramsCapacity * 2;
                                        var newParams = new Array(newParamsCapacity);
                                        java.lang.System.arraycopy(params, 0, newParams, 0, paramsCapacity);
                                        params = newParams;
                                        paramsCapacity = newParamsCapacity;
                                    }
                                    params[paramsIndex] = param_1;
                                    paramsIndex++;
                                }
                                previous = cursor + 1;
                            }
                            cursor++;
                        }
                        var param = templatedParams.substring(previous, cursor);
                        if (param.length > 0) {
                            if (paramsIndex >= paramsCapacity) {
                                var newParamsCapacity = paramsCapacity * 2;
                                var newParams = new Array(newParamsCapacity);
                                java.lang.System.arraycopy(params, 0, newParams, 0, paramsCapacity);
                                params = newParams;
                                paramsCapacity = newParamsCapacity;
                            }
                            params[paramsIndex] = param;
                            paramsIndex++;
                        }
                        if (paramsIndex < params.length) {
                            var shrinked = new Array(paramsIndex);
                            java.lang.System.arraycopy(params, 0, shrinked, 0, paramsIndex);
                            params = shrinked;
                        }
                        this.subAction = actionFactory(params);
                        if (this.subAction != null) {
                            this.subAction.eval(context);
                        }
                        else {
                            context.continueTask();
                        }
                    };
                    ActionPlugin.prototype.toString = function () {
                        return this._actionName + "(" + this._flatParams + ")";
                    };
                    return ActionPlugin;
                }(org.mwg.plugin.AbstractTaskAction));
                task.ActionPlugin = ActionPlugin;
                var ActionPrint = (function (_super) {
                    __extends(ActionPrint, _super);
                    function ActionPrint(p_name, withLineBreak) {
                        _super.call(this);
                        this._name = p_name;
                        this._withLineBreak = withLineBreak;
                    }
                    ActionPrint.prototype.eval = function (context) {
                        if (this._withLineBreak) {
                            console.log(context.template(this._name));
                        }
                        else {
                            console.log(context.template(this._name));
                        }
                        context.continueTask();
                    };
                    ActionPrint.prototype.toString = function () {
                        return "print(\'" + this._name + "\')";
                    };
                    return ActionPrint;
                }(org.mwg.plugin.AbstractTaskAction));
                task.ActionPrint = ActionPrint;
                var ActionProperties = (function (_super) {
                    __extends(ActionProperties, _super);
                    function ActionProperties(filterType) {
                        _super.call(this);
                        this._filter = filterType;
                    }
                    ActionProperties.prototype.eval = function (context) {
                        var _this = this;
                        var previous = context.result();
                        var result = context.newResult();
                        for (var i = 0; i < previous.size(); i++) {
                            if (previous.get(i) instanceof org.mwg.plugin.AbstractNode) {
                                var n = previous.get(i);
                                var nState = context.graph().resolver().resolveState(n);
                                nState.each(function (attributeKey, elemType, elem) {
                                    {
                                        if (_this._filter == -1 || elemType == _this._filter) {
                                            var retrieved = context.graph().resolver().hashToString(attributeKey);
                                            if (retrieved != null) {
                                                result.add(retrieved);
                                            }
                                            else {
                                                result.add(attributeKey);
                                            }
                                        }
                                    }
                                });
                                n.free();
                            }
                        }
                        previous.clear();
                        context.continueWith(result);
                    };
                    return ActionProperties;
                }(org.mwg.plugin.AbstractTaskAction));
                task.ActionProperties = ActionProperties;
                var ActionRemove = (function (_super) {
                    __extends(ActionRemove, _super);
                    function ActionRemove(relationName, variableNameToRemove) {
                        _super.call(this);
                        this._relationName = relationName;
                        this._variableNameToRemove = variableNameToRemove;
                    }
                    ActionRemove.prototype.eval = function (context) {
                        var previousResult = context.result();
                        var savedVar = context.variable(context.template(this._variableNameToRemove));
                        if (previousResult != null && savedVar != null) {
                            var relName = context.template(this._relationName);
                            var previousResultIt = previousResult.iterator();
                            var iter = previousResultIt.next();
                            while (iter != null) {
                                if (iter instanceof org.mwg.plugin.AbstractNode) {
                                    var savedVarIt = savedVar.iterator();
                                    var toRemoveIter = savedVarIt.next();
                                    while (toRemoveIter != null) {
                                        if (toRemoveIter instanceof org.mwg.plugin.AbstractNode) {
                                            iter.remove(relName, toRemoveIter);
                                        }
                                        toRemoveIter = savedVarIt.next();
                                    }
                                }
                                iter = previousResultIt.next();
                            }
                        }
                        context.continueTask();
                    };
                    ActionRemove.prototype.toString = function () {
                        return "remove(\'" + this._relationName + "\'" + org.mwg.Constants.QUERY_SEP + "\'" + this._variableNameToRemove + "\')";
                    };
                    return ActionRemove;
                }(org.mwg.plugin.AbstractTaskAction));
                task.ActionRemove = ActionRemove;
                var ActionRemoveProperty = (function (_super) {
                    __extends(ActionRemoveProperty, _super);
                    function ActionRemoveProperty(propertyName) {
                        _super.call(this);
                        this._propertyName = propertyName;
                    }
                    ActionRemoveProperty.prototype.eval = function (context) {
                        var previousResult = context.result();
                        if (previousResult != null) {
                            var flatRelationName = context.template(this._propertyName);
                            for (var i = 0; i < previousResult.size(); i++) {
                                var loopObj = previousResult.get(i);
                                if (loopObj instanceof org.mwg.plugin.AbstractNode) {
                                    var loopNode = loopObj;
                                    loopNode.removeProperty(flatRelationName);
                                }
                            }
                        }
                        context.continueTask();
                    };
                    ActionRemoveProperty.prototype.toString = function () {
                        return "removeProperty(\'" + this._propertyName + "\')";
                    };
                    return ActionRemoveProperty;
                }(org.mwg.plugin.AbstractTaskAction));
                task.ActionRemoveProperty = ActionRemoveProperty;
                var ActionSave = (function (_super) {
                    __extends(ActionSave, _super);
                    function ActionSave() {
                        _super.apply(this, arguments);
                    }
                    ActionSave.prototype.eval = function (context) {
                        context.graph().save(function (result) {
                            {
                                context.continueTask();
                            }
                        });
                    };
                    ActionSave.prototype.toString = function () {
                        return "save()";
                    };
                    return ActionSave;
                }(org.mwg.plugin.AbstractTaskAction));
                task.ActionSave = ActionSave;
                var ActionSelect = (function (_super) {
                    __extends(ActionSelect, _super);
                    function ActionSelect(p_filter) {
                        _super.call(this);
                        this._filter = p_filter;
                    }
                    ActionSelect.prototype.eval = function (context) {
                        var previous = context.result();
                        var next = context.newResult();
                        var previousSize = previous.size();
                        for (var i = 0; i < previousSize; i++) {
                            var obj = previous.get(i);
                            if (obj instanceof org.mwg.plugin.AbstractNode) {
                                var casted = obj;
                                if (this._filter(casted)) {
                                    next.add(casted);
                                }
                                else {
                                    casted.free();
                                }
                            }
                            else {
                                next.add(obj);
                            }
                        }
                        previous.clear();
                        context.continueWith(next);
                    };
                    ActionSelect.prototype.toString = function () {
                        return "select()";
                    };
                    return ActionSelect;
                }(org.mwg.plugin.AbstractTaskAction));
                task.ActionSelect = ActionSelect;
                var ActionSelectObject = (function (_super) {
                    __extends(ActionSelectObject, _super);
                    function ActionSelectObject(filterFunction) {
                        _super.call(this);
                        this._filter = filterFunction;
                    }
                    ActionSelectObject.prototype.eval = function (context) {
                        var previous = context.result();
                        var next = context.wrap(null);
                        var iterator = previous.iterator();
                        var nextElem = iterator.next();
                        while (nextElem != null) {
                            if (this._filter(nextElem, context)) {
                                if (nextElem instanceof org.mwg.plugin.AbstractNode) {
                                    var casted = nextElem;
                                    next.add(casted.graph().cloneNode(casted));
                                }
                                else {
                                    next.add(nextElem);
                                }
                            }
                            nextElem = iterator.next();
                        }
                        context.continueWith(next);
                    };
                    ActionSelectObject.prototype.toString = function () {
                        return "selectObject()";
                    };
                    return ActionSelectObject;
                }(org.mwg.plugin.AbstractTaskAction));
                task.ActionSelectObject = ActionSelectObject;
                var ActionSetProperty = (function (_super) {
                    __extends(ActionSetProperty, _super);
                    function ActionSetProperty(relationName, propertyType, variableNameToSet, force) {
                        _super.call(this);
                        this._relationName = relationName;
                        this._variableNameToSet = variableNameToSet;
                        this._propertyType = propertyType;
                        this._force = force;
                    }
                    ActionSetProperty.prototype.eval = function (context) {
                        var previousResult = context.result();
                        var flatRelationName = context.template(this._relationName);
                        if (previousResult != null) {
                            var toSet = void 0;
                            var templateBased = context.template(this._variableNameToSet);
                            switch (this._propertyType) {
                                case org.mwg.Type.BOOL:
                                    toSet = this.parseBoolean(templateBased.toString());
                                    break;
                                case org.mwg.Type.INT:
                                    toSet = org.mwg.core.task.TaskHelper.parseInt(templateBased.toString());
                                    break;
                                case org.mwg.Type.DOUBLE:
                                    toSet = parseFloat(templateBased.toString());
                                    break;
                                case org.mwg.Type.LONG:
                                    toSet = java.lang.Long.parseLong(templateBased.toString());
                                    break;
                                default:
                                    toSet = templateBased;
                            }
                            for (var i = 0; i < previousResult.size(); i++) {
                                var loopObj = previousResult.get(i);
                                if (loopObj instanceof org.mwg.plugin.AbstractNode) {
                                    var loopNode = loopObj;
                                    if (this._force) {
                                        loopNode.forceProperty(flatRelationName, this._propertyType, toSet);
                                    }
                                    else {
                                        loopNode.setProperty(flatRelationName, this._propertyType, toSet);
                                    }
                                }
                            }
                        }
                        context.continueTask();
                    };
                    ActionSetProperty.prototype.toString = function () {
                        return "setProperty(\'" + this._relationName + "\'" + org.mwg.Constants.QUERY_SEP + "\'" + this._propertyType + "\'" + org.mwg.Constants.QUERY_SEP + "\'" + this._variableNameToSet + "\')";
                    };
                    ActionSetProperty.prototype.parseBoolean = function (booleanValue) {
                        var lower = booleanValue.toLowerCase();
                        return (lower === "true" || lower === "1");
                    };
                    return ActionSetProperty;
                }(org.mwg.plugin.AbstractTaskAction));
                task.ActionSetProperty = ActionSetProperty;
                var ActionSplit = (function (_super) {
                    __extends(ActionSplit, _super);
                    function ActionSplit(p_splitPattern) {
                        _super.call(this);
                        this._splitPattern = p_splitPattern;
                    }
                    ActionSplit.prototype.eval = function (context) {
                        var splitPattern = context.template(this._splitPattern);
                        var previous = context.result();
                        var next = context.wrap(null);
                        for (var i = 0; i < previous.size(); i++) {
                            var loop = previous.get(0);
                            if (loop instanceof String) {
                                var splitted = loop.split(splitPattern);
                                if (previous.size() == 1) {
                                    for (var j = 0; j < splitted.length; j++) {
                                        next.add(splitted[j]);
                                    }
                                }
                                else {
                                    next.add(splitted);
                                }
                            }
                        }
                        context.continueWith(next);
                    };
                    ActionSplit.prototype.toString = function () {
                        return "split(\'" + this._splitPattern + "\')";
                    };
                    return ActionSplit;
                }(org.mwg.plugin.AbstractTaskAction));
                task.ActionSplit = ActionSplit;
                var ActionSubTask = (function (_super) {
                    __extends(ActionSubTask, _super);
                    function ActionSubTask(p_subTask) {
                        _super.call(this);
                        this._subTask = p_subTask;
                    }
                    ActionSubTask.prototype.eval = function (context) {
                        var previous = context.result();
                        this._subTask.executeFrom(context, previous, org.mwg.plugin.SchedulerAffinity.SAME_THREAD, function (subTaskResult) {
                            {
                                context.continueWith(subTaskResult);
                            }
                        });
                    };
                    ActionSubTask.prototype.toString = function () {
                        return "subTask()";
                    };
                    return ActionSubTask;
                }(org.mwg.plugin.AbstractTaskAction));
                task.ActionSubTask = ActionSubTask;
                var ActionSubTasks = (function (_super) {
                    __extends(ActionSubTasks, _super);
                    function ActionSubTasks(p_subTasks) {
                        _super.call(this);
                        this._subTasks = p_subTasks;
                    }
                    ActionSubTasks.prototype.eval = function (context) {
                        var _this = this;
                        var previous = context.result();
                        var cursor = new java.util.concurrent.atomic.AtomicInteger(0);
                        var tasksSize = this._subTasks.length;
                        var next = context.newResult();
                        var loopcb = new Array(1);
                        loopcb[0] = function (result) {
                            {
                                var current_2 = cursor.getAndIncrement();
                                if (result != null) {
                                    for (var i = 0; i < result.size(); i++) {
                                        var loop = result.get(i);
                                        if (loop != null) {
                                            next.add(loop);
                                        }
                                    }
                                }
                                if (current_2 < tasksSize) {
                                    _this._subTasks[current_2].executeFrom(context, previous, org.mwg.plugin.SchedulerAffinity.SAME_THREAD, loopcb[0]);
                                }
                                else {
                                    context.continueWith(next);
                                }
                            }
                        };
                        var current = cursor.getAndIncrement();
                        if (current < tasksSize) {
                            this._subTasks[current].executeFrom(context, previous, org.mwg.plugin.SchedulerAffinity.SAME_THREAD, loopcb[0]);
                        }
                        else {
                            context.continueWith(next);
                        }
                    };
                    ActionSubTasks.prototype.toString = function () {
                        return "subTasks()";
                    };
                    return ActionSubTasks;
                }(org.mwg.plugin.AbstractTaskAction));
                task.ActionSubTasks = ActionSubTasks;
                var ActionSubTasksPar = (function (_super) {
                    __extends(ActionSubTasksPar, _super);
                    function ActionSubTasksPar(p_subTasks) {
                        _super.call(this);
                        this._subTasks = p_subTasks;
                    }
                    ActionSubTasksPar.prototype.eval = function (context) {
                        var previous = context.result();
                        var next = context.newResult();
                        var subTasksSize = this._subTasks.length;
                        next.allocate(subTasksSize);
                        var waiter = context.graph().newCounter(subTasksSize);
                        var _loop_7 = function(i) {
                            var finalI = i;
                            this_3._subTasks[i].executeFrom(context, previous, org.mwg.plugin.SchedulerAffinity.ANY_LOCAL_THREAD, function (subTaskResult) {
                                {
                                    next.set(finalI, subTaskResult);
                                    waiter.count();
                                }
                            });
                        };
                        var this_3 = this;
                        for (var i = 0; i < subTasksSize; i++) {
                            _loop_7(i);
                        }
                        waiter.then(function () {
                            {
                                context.continueWith(next);
                            }
                        });
                    };
                    ActionSubTasksPar.prototype.toString = function () {
                        return "subTasksPar()";
                    };
                    return ActionSubTasksPar;
                }(org.mwg.plugin.AbstractTaskAction));
                task.ActionSubTasksPar = ActionSubTasksPar;
                var ActionTime = (function (_super) {
                    __extends(ActionTime, _super);
                    function ActionTime(p_varName) {
                        _super.call(this);
                        this._varName = p_varName;
                    }
                    ActionTime.prototype.eval = function (context) {
                        var flat = context.template(this._varName);
                        context.setTime(java.lang.Long.parseLong(flat));
                        context.continueTask();
                    };
                    ActionTime.prototype.toString = function () {
                        return "setTime(\'" + this._varName + "\')";
                    };
                    return ActionTime;
                }(org.mwg.plugin.AbstractTaskAction));
                task.ActionTime = ActionTime;
                var ActionTraverse = (function (_super) {
                    __extends(ActionTraverse, _super);
                    function ActionTraverse(p_name) {
                        _super.call(this);
                        this._name = p_name;
                    }
                    ActionTraverse.prototype.eval = function (context) {
                        var previousResult = context.result();
                        if (previousResult != null) {
                            var previousSize = previousResult.size();
                            var isConsistent = true;
                            var world = org.mwg.Constants.NULL_LONG;
                            var time = org.mwg.Constants.NULL_LONG;
                            for (var i = 0; i < previousSize; i++) {
                                var loop = previousResult.get(i);
                                if (loop instanceof org.mwg.plugin.AbstractNode) {
                                    var casted = loop;
                                    if (time == org.mwg.Constants.NULL_LONG) {
                                        time = casted.time();
                                        world = casted.world();
                                    }
                                    else {
                                        if (casted.world() != world || casted.time() != time) {
                                            isConsistent = false;
                                            break;
                                        }
                                    }
                                }
                            }
                            var flatName = context.template(this._name);
                            if (isConsistent) {
                                var collected = new java.util.ArrayList();
                                for (var i = 0; i < previousSize; i++) {
                                    var loop = previousResult.get(i);
                                    if (loop instanceof org.mwg.plugin.AbstractNode) {
                                        var casted = loop;
                                        if (casted.type(flatName) == org.mwg.Type.RELATION) {
                                            var flatRel = casted.get(flatName);
                                            if (flatRel != null) {
                                                for (var j = 0; j < flatRel.size(); j++) {
                                                    collected.add(flatRel.get(j));
                                                }
                                            }
                                        }
                                        casted.free();
                                    }
                                }
                                previousResult.clear();
                                var flatCollected = new Float64Array(collected.size());
                                for (var i = 0; i < collected.size(); i++) {
                                    flatCollected[i] = collected.get(i);
                                }
                                context.graph().lookupAll(world, time, flatCollected, function (result) {
                                    {
                                        context.continueWith(context.wrap(result));
                                    }
                                });
                            }
                            else {
                                var finalResult_2 = context.newResult();
                                var defer_2 = context.graph().newCounter(previousSize);
                                var _loop_8 = function(i) {
                                    var loop = previousResult.get(i);
                                    if (loop instanceof org.mwg.plugin.AbstractNode) {
                                        var casted_2 = loop;
                                        casted_2.rel(flatName, function (result) {
                                            {
                                                if (result != null) {
                                                    for (var j = 0; j < result.length; j++) {
                                                        finalResult_2.add(result[j]);
                                                    }
                                                }
                                                casted_2.free();
                                                defer_2.count();
                                            }
                                        });
                                    }
                                    else {
                                        finalResult_2.add(loop);
                                        defer_2.count();
                                    }
                                };
                                for (var i = 0; i < previousSize; i++) {
                                    _loop_8(i);
                                }
                                defer_2.then(function () {
                                    {
                                        previousResult.clear();
                                        context.continueWith(finalResult_2);
                                    }
                                });
                            }
                        }
                        else {
                            context.continueTask();
                        }
                    };
                    ActionTraverse.prototype.toString = function () {
                        return "traverse(\'" + this._name + "\')";
                    };
                    return ActionTraverse;
                }(org.mwg.plugin.AbstractTaskAction));
                task.ActionTraverse = ActionTraverse;
                var ActionTraverseIndex = (function (_super) {
                    __extends(ActionTraverseIndex, _super);
                    function ActionTraverseIndex(indexName) {
                        var queryParams = [];
                        for (var _i = 1; _i < arguments.length; _i++) {
                            queryParams[_i - 1] = arguments[_i];
                        }
                        _super.call(this);
                        this._queryParams = queryParams;
                        this._indexName = indexName;
                        this._resolvedQueryParams = new Array(queryParams.length);
                    }
                    ActionTraverseIndex.prototype.eval = function (context) {
                        var finalResult = context.wrap(null);
                        var flatName = context.template(this._indexName);
                        for (var i = 0; i < this._queryParams.length; i++) {
                            this._resolvedQueryParams[i] = context.template(this._queryParams[i]);
                        }
                        var query = context.graph().newQuery();
                        query.setWorld(context.world());
                        query.setTime(context.time());
                        query.setIndexName(flatName);
                        for (var i = 0; i < this._resolvedQueryParams.length; i = i + 2) {
                            query.add(this._resolvedQueryParams[i], this._resolvedQueryParams[i + 1]);
                        }
                        var previousResult = context.result();
                        if (previousResult != null) {
                            var previousSize = previousResult.size();
                            var defer_3 = context.graph().newCounter(previousSize);
                            var _loop_9 = function(i) {
                                var loop = previousResult.get(i);
                                if (loop instanceof org.mwg.plugin.AbstractNode) {
                                    var casted_3 = loop;
                                    casted_3.findByQuery(query, function (result) {
                                        {
                                            if (result != null) {
                                                for (var j = 0; j < result.length; j++) {
                                                    if (result[j] != null) {
                                                        finalResult.add(result[j]);
                                                    }
                                                }
                                            }
                                            casted_3.free();
                                            defer_3.count();
                                        }
                                    });
                                }
                                else {
                                    finalResult.add(loop);
                                    defer_3.count();
                                }
                            };
                            for (var i = 0; i < previousSize; i++) {
                                _loop_9(i);
                            }
                            defer_3.then(function () {
                                {
                                    previousResult.clear();
                                    context.continueWith(finalResult);
                                }
                            });
                        }
                        else {
                            context.continueTask();
                        }
                    };
                    ActionTraverseIndex.prototype.toString = function () {
                        return "traverseIndex(\'" + this._indexName + org.mwg.core.CoreConstants.QUERY_SEP + java.lang.String.join(",", this._resolvedQueryParams) + "\')";
                    };
                    return ActionTraverseIndex;
                }(org.mwg.plugin.AbstractTaskAction));
                task.ActionTraverseIndex = ActionTraverseIndex;
                var ActionTraverseIndexAll = (function (_super) {
                    __extends(ActionTraverseIndexAll, _super);
                    function ActionTraverseIndexAll(indexName) {
                        _super.call(this);
                        this._indexName = indexName;
                    }
                    ActionTraverseIndexAll.prototype.eval = function (context) {
                        var finalResult = context.wrap(null);
                        var flatName = context.template(this._indexName);
                        var previousResult = context.result();
                        if (previousResult != null) {
                            var previousSize = previousResult.size();
                            var defer_4 = context.graph().newCounter(previousSize);
                            var _loop_10 = function(i) {
                                var loop = previousResult.get(i);
                                if (loop instanceof org.mwg.plugin.AbstractNode) {
                                    var casted_4 = loop;
                                    casted_4.findAll(flatName, function (result) {
                                        {
                                            if (result != null) {
                                                for (var j = 0; j < result.length; j++) {
                                                    if (result[j] != null) {
                                                        finalResult.add(result[j]);
                                                    }
                                                }
                                            }
                                            casted_4.free();
                                            defer_4.count();
                                        }
                                    });
                                }
                                else {
                                    finalResult.add(loop);
                                    defer_4.count();
                                }
                            };
                            for (var i = 0; i < previousSize; i++) {
                                _loop_10(i);
                            }
                            defer_4.then(function () {
                                {
                                    previousResult.clear();
                                    context.continueWith(finalResult);
                                }
                            });
                        }
                        else {
                            context.continueTask();
                        }
                    };
                    ActionTraverseIndexAll.prototype.toString = function () {
                        return "traverseIndexAll(\'" + this._indexName + "\')";
                    };
                    return ActionTraverseIndexAll;
                }(org.mwg.plugin.AbstractTaskAction));
                task.ActionTraverseIndexAll = ActionTraverseIndexAll;
                var ActionTraverseOrKeep = (function (_super) {
                    __extends(ActionTraverseOrKeep, _super);
                    function ActionTraverseOrKeep(p_name) {
                        _super.call(this);
                        this._name = p_name;
                    }
                    ActionTraverseOrKeep.prototype.eval = function (context) {
                        var flatName = context.template(this._name);
                        var previousResult = context.result();
                        if (previousResult != null) {
                            var finalResult_3 = context.newResult();
                            var previousSize = previousResult.size();
                            var defer_5 = context.graph().newCounter(previousSize);
                            for (var i = 0; i < previousSize; i++) {
                                var loop = previousResult.get(i);
                                if (loop instanceof org.mwg.plugin.AbstractNode) {
                                    var casted = loop;
                                    if (casted.type(flatName) == org.mwg.Type.RELATION) {
                                        casted.rel(flatName, function (result) {
                                            {
                                                if (result != null) {
                                                    for (var j = 0; j < result.length; j++) {
                                                        finalResult_3.add(result[j]);
                                                    }
                                                }
                                                defer_5.count();
                                            }
                                        });
                                    }
                                    else {
                                        finalResult_3.add(casted.graph().cloneNode(casted));
                                        defer_5.count();
                                    }
                                }
                                else {
                                    finalResult_3.add(loop);
                                    defer_5.count();
                                }
                            }
                            defer_5.then(function () {
                                {
                                    context.continueWith(finalResult_3);
                                }
                            });
                        }
                        else {
                            context.continueTask();
                        }
                    };
                    ActionTraverseOrKeep.prototype.toString = function () {
                        return "traverseOrKeep(\'" + this._name + "\')";
                    };
                    return ActionTraverseOrKeep;
                }(org.mwg.plugin.AbstractTaskAction));
                task.ActionTraverseOrKeep = ActionTraverseOrKeep;
                var ActionWhileDo = (function (_super) {
                    __extends(ActionWhileDo, _super);
                    function ActionWhileDo(p_cond, p_then) {
                        _super.call(this);
                        this._cond = p_cond;
                        this._then = p_then;
                    }
                    ActionWhileDo.prototype.eval = function (context) {
                        var _this = this;
                        var coreTaskContext = context;
                        var selfPointer = this;
                        var recursiveAction = new Array(1);
                        recursiveAction[0] = function (res) {
                            {
                                var previous = coreTaskContext._result;
                                coreTaskContext._result = res;
                                if (_this._cond(context)) {
                                    if (previous != null) {
                                        previous.free();
                                    }
                                    selfPointer._then.executeFrom(context, context._result, org.mwg.plugin.SchedulerAffinity.SAME_THREAD, recursiveAction[0]);
                                }
                                else {
                                    if (previous != null) {
                                        previous.free();
                                    }
                                    context.continueWith(res);
                                }
                            }
                        };
                        if (this._cond(context)) {
                            this._then.executeFrom(context, coreTaskContext._result, org.mwg.plugin.SchedulerAffinity.SAME_THREAD, recursiveAction[0]);
                        }
                        else {
                            context.continueTask();
                        }
                    };
                    ActionWhileDo.prototype.toString = function () {
                        return "whileDo()";
                    };
                    return ActionWhileDo;
                }(org.mwg.plugin.AbstractTaskAction));
                task.ActionWhileDo = ActionWhileDo;
                var ActionWith = (function (_super) {
                    __extends(ActionWith, _super);
                    function ActionWith(name, stringPattern) {
                        _super.call(this);
                        this._patternTemplate = stringPattern;
                        this._name = name;
                    }
                    ActionWith.prototype.toString = function () {
                        return "with(\'" + this._name + "\'" + org.mwg.Constants.QUERY_SEP + "\'" + this._patternTemplate + "\')";
                    };
                    ActionWith.prototype.eval = function (context) {
                        var pattern = new RegExp(context.template(this._patternTemplate));
                        var previous = context.result();
                        var next = context.newResult();
                        var previousSize = previous.size();
                        for (var i = 0; i < previousSize; i++) {
                            var obj = previous.get(i);
                            if (obj instanceof org.mwg.plugin.AbstractNode) {
                                var casted = obj;
                                var currentName = casted.get(this._name);
                                if (currentName != null && pattern.test(currentName.toString())) {
                                    next.add(casted.graph().cloneNode(casted));
                                }
                            }
                            else {
                                next.add(obj);
                            }
                        }
                        context.continueWith(next);
                    };
                    return ActionWith;
                }(org.mwg.plugin.AbstractTaskAction));
                task.ActionWith = ActionWith;
                var ActionWithout = (function (_super) {
                    __extends(ActionWithout, _super);
                    function ActionWithout(name, stringPattern) {
                        _super.call(this);
                        this._patternTemplate = stringPattern;
                        this._name = name;
                    }
                    ActionWithout.prototype.toString = function () {
                        return "without(\'" + this._name + "\'" + org.mwg.Constants.QUERY_SEP + "\'" + this._patternTemplate + "\')";
                    };
                    ActionWithout.prototype.eval = function (context) {
                        var pattern = new RegExp(context.template(this._patternTemplate));
                        var previous = context.result();
                        var next = context.newResult();
                        var previousSize = previous.size();
                        for (var i = 0; i < previousSize; i++) {
                            var obj = previous.get(i);
                            if (obj instanceof org.mwg.plugin.AbstractNode) {
                                var casted = obj;
                                var currentName = casted.get(this._name);
                                if (currentName == null || !pattern.test(currentName.toString())) {
                                    next.add(casted.graph().cloneNode(casted));
                                }
                            }
                            else {
                                next.add(obj);
                            }
                        }
                        context.continueWith(next);
                    };
                    return ActionWithout;
                }(org.mwg.plugin.AbstractTaskAction));
                task.ActionWithout = ActionWithout;
                var ActionWorld = (function (_super) {
                    __extends(ActionWorld, _super);
                    function ActionWorld(p_varName) {
                        _super.call(this);
                        this._varName = p_varName;
                    }
                    ActionWorld.prototype.eval = function (context) {
                        var flat = context.template(this._varName);
                        context.setWorld(java.lang.Long.parseLong(flat));
                        context.continueTask();
                    };
                    ActionWorld.prototype.toString = function () {
                        return "setWorld(\'" + this._varName + "\')";
                    };
                    return ActionWorld;
                }(org.mwg.plugin.AbstractTaskAction));
                task.ActionWorld = ActionWorld;
                var ActionWrapper = (function (_super) {
                    __extends(ActionWrapper, _super);
                    function ActionWrapper(p_wrapped) {
                        _super.call(this);
                        this._wrapped = p_wrapped;
                    }
                    ActionWrapper.prototype.eval = function (context) {
                        this._wrapped(context);
                    };
                    ActionWrapper.prototype.toString = function () {
                        return "then()";
                    };
                    return ActionWrapper;
                }(org.mwg.plugin.AbstractTaskAction));
                task.ActionWrapper = ActionWrapper;
                var CoreTask = (function () {
                    function CoreTask() {
                        this._first = null;
                        this._last = null;
                        this._hookFactory = null;
                    }
                    CoreTask.prototype.addAction = function (nextAction) {
                        if (this._first == null) {
                            this._first = nextAction;
                            this._last = this._first;
                        }
                        else {
                            this._last.setNext(nextAction);
                            this._last = nextAction;
                        }
                    };
                    CoreTask.prototype.setWorld = function (template) {
                        this.addAction(new org.mwg.core.task.ActionWorld(template));
                        return this;
                    };
                    CoreTask.prototype.setTime = function (template) {
                        this.addAction(new org.mwg.core.task.ActionTime(template));
                        return this;
                    };
                    CoreTask.prototype.fromIndex = function (indexName, query) {
                        if (indexName == null) {
                            throw new Error("indexName should not be null");
                        }
                        if (query == null) {
                            throw new Error("query should not be null");
                        }
                        this.addAction(new org.mwg.core.task.ActionFromIndex(indexName, query));
                        return this;
                    };
                    CoreTask.prototype.fromIndexAll = function (indexName) {
                        if (indexName == null) {
                            throw new Error("indexName should not be null");
                        }
                        this.addAction(new org.mwg.core.task.ActionFromIndexAll(indexName));
                        return this;
                    };
                    CoreTask.prototype.indexNode = function (indexName, flatKeyAttributes) {
                        this.addAction(new org.mwg.core.task.ActionIndexOrUnindexNode(indexName, flatKeyAttributes, true));
                        return this;
                    };
                    CoreTask.prototype.indexNodeAt = function (world, time, indexName, flatKeyAttributes) {
                        this.addAction(new org.mwg.core.task.ActionIndexOrUnindexNodeAt(world, time, indexName, flatKeyAttributes, true));
                        return this;
                    };
                    CoreTask.prototype.localIndex = function (indexedRelation, flatKeyAttributes, varNodeToAdd) {
                        this.addAction(new org.mwg.core.task.ActionLocalIndexOrUnindex(indexedRelation, flatKeyAttributes, varNodeToAdd, true));
                        return this;
                    };
                    CoreTask.prototype.unindexNodeAt = function (world, time, indexName, flatKeyAttributes) {
                        this.addAction(new org.mwg.core.task.ActionIndexOrUnindexNodeAt(world, time, indexName, flatKeyAttributes, true));
                        return this;
                    };
                    CoreTask.prototype.unindexNode = function (indexName, flatKeyAttributes) {
                        this.addAction(new org.mwg.core.task.ActionIndexOrUnindexNode(indexName, flatKeyAttributes, false));
                        return this;
                    };
                    CoreTask.prototype.localUnindex = function (indexedRelation, flatKeyAttributes, varNodeToAdd) {
                        this.addAction(new org.mwg.core.task.ActionLocalIndexOrUnindex(indexedRelation, flatKeyAttributes, varNodeToAdd, false));
                        return this;
                    };
                    CoreTask.prototype.indexesNames = function () {
                        this.addAction(new org.mwg.core.task.ActionIndexesNames());
                        return this;
                    };
                    CoreTask.prototype.selectWith = function (name, pattern) {
                        if (pattern == null) {
                            throw new Error("pattern should not be null");
                        }
                        this.addAction(new org.mwg.core.task.ActionWith(name, pattern));
                        return this;
                    };
                    CoreTask.prototype.selectWithout = function (name, pattern) {
                        if (pattern == null) {
                            throw new Error("pattern should not be null");
                        }
                        this.addAction(new org.mwg.core.task.ActionWithout(name, pattern));
                        return this;
                    };
                    CoreTask.prototype.asGlobalVar = function (variableName) {
                        if (variableName == null) {
                            throw new Error("variableName should not be null");
                        }
                        this.addAction(new org.mwg.core.task.ActionAsVar(variableName, true));
                        return this;
                    };
                    CoreTask.prototype.asVar = function (variableName) {
                        if (variableName == null) {
                            throw new Error("variableName should not be null");
                        }
                        this.addAction(new org.mwg.core.task.ActionAsVar(variableName, false));
                        return this;
                    };
                    CoreTask.prototype.defineVar = function (variableName) {
                        if (variableName == null) {
                            throw new Error("variableName should not be null");
                        }
                        this.addAction(new org.mwg.core.task.ActionDefineVar(variableName));
                        return this;
                    };
                    CoreTask.prototype.addToGlobalVar = function (variableName) {
                        if (variableName == null) {
                            throw new Error("variableName should not be null");
                        }
                        this.addAction(new org.mwg.core.task.ActionAddToVar(variableName, true));
                        return this;
                    };
                    CoreTask.prototype.addToVar = function (variableName) {
                        if (variableName == null) {
                            throw new Error("variableName should not be null");
                        }
                        this.addAction(new org.mwg.core.task.ActionAddToVar(variableName, false));
                        return this;
                    };
                    CoreTask.prototype.fromVar = function (variableName) {
                        if (variableName == null) {
                            throw new Error("variableName should not be null");
                        }
                        this.addAction(new org.mwg.core.task.ActionFromVar(variableName, -1));
                        return this;
                    };
                    CoreTask.prototype.fromVarAt = function (variableName, index) {
                        if (variableName == null) {
                            throw new Error("variableName should not be null");
                        }
                        this.addAction(new org.mwg.core.task.ActionFromVar(variableName, index));
                        return this;
                    };
                    CoreTask.prototype.select = function (filter) {
                        if (filter == null) {
                            throw new Error("filter should not be null");
                        }
                        this.addAction(new org.mwg.core.task.ActionSelect(filter));
                        return this;
                    };
                    CoreTask.prototype.selectObject = function (filterFunction) {
                        if (filterFunction == null) {
                            throw new Error("filterFunction should not be null");
                        }
                        this.addAction(new org.mwg.core.task.ActionSelectObject(filterFunction));
                        return this;
                    };
                    CoreTask.prototype.selectWhere = function (subTask) {
                        throw new Error("Not implemented yet");
                    };
                    CoreTask.prototype.get = function (name) {
                        this.addAction(new org.mwg.core.task.ActionGet(name));
                        return this;
                    };
                    CoreTask.prototype.traverse = function (relationName) {
                        this.addAction(new org.mwg.core.task.ActionTraverse(relationName));
                        return this;
                    };
                    CoreTask.prototype.traverseOrKeep = function (relationName) {
                        this.addAction(new org.mwg.core.task.ActionTraverseOrKeep(relationName));
                        return this;
                    };
                    CoreTask.prototype.traverseIndex = function (indexName) {
                        var queryParams = [];
                        for (var _i = 1; _i < arguments.length; _i++) {
                            queryParams[_i - 1] = arguments[_i];
                        }
                        if (indexName == null) {
                            throw new Error("indexName should not be null");
                        }
                        if (queryParams.length % 2 != 0) {
                            throw new Error("The number of arguments in the queryParams MUST be even, because it should be a sequence of \"key\",\"value\". Current size: " + queryParams.length);
                        }
                        this.addAction(new ((_a = org.mwg.core.task.ActionTraverseIndex).bind.apply(_a, [void 0].concat([indexName], queryParams)))());
                        return this;
                        var _a;
                    };
                    CoreTask.prototype.traverseIndexAll = function (indexName) {
                        if (indexName == null) {
                            throw new Error("indexName should not be null");
                        }
                        this.addAction(new org.mwg.core.task.ActionTraverseIndexAll(indexName));
                        return this;
                    };
                    CoreTask.prototype.map = function (mapFunction) {
                        if (mapFunction == null) {
                            throw new Error("mapFunction should not be null");
                        }
                        this.addAction(new org.mwg.core.task.ActionMap(mapFunction));
                        return this;
                    };
                    CoreTask.prototype.group = function (groupFunction) {
                        throw new Error("Not implemented yet");
                    };
                    CoreTask.prototype.groupWhere = function (groupSubTask) {
                        throw new Error("Not implemented yet");
                    };
                    CoreTask.prototype.inject = function (inputValue) {
                        if (inputValue == null) {
                            throw new Error("inputValue should not be null");
                        }
                        this.addAction(new org.mwg.core.task.ActionInject(inputValue));
                        return this;
                    };
                    CoreTask.prototype.subTask = function (subTask) {
                        if (subTask == null) {
                            throw new Error("subTask should not be null");
                        }
                        this.addAction(new org.mwg.core.task.ActionSubTask(subTask));
                        return this;
                    };
                    CoreTask.prototype.isolate = function (subTask) {
                        if (subTask == null) {
                            throw new Error("subTask should not be null");
                        }
                        this.addAction(new org.mwg.core.task.ActionIsolate(subTask));
                        return this;
                    };
                    CoreTask.prototype.subTasks = function (subTasks) {
                        if (subTasks == null) {
                            throw new Error("subTask should not be null");
                        }
                        this.addAction(new org.mwg.core.task.ActionSubTasks(subTasks));
                        return this;
                    };
                    CoreTask.prototype.subTasksPar = function (subTasks) {
                        if (subTasks == null) {
                            throw new Error("subTask should not be null");
                        }
                        this.addAction(new org.mwg.core.task.ActionSubTasksPar(subTasks));
                        return this;
                    };
                    CoreTask.prototype.ifThen = function (cond, then) {
                        if (cond == null) {
                            throw new Error("condition should not be null");
                        }
                        if (then == null) {
                            throw new Error("subTask should not be null");
                        }
                        this.addAction(new org.mwg.core.task.ActionIfThen(cond, then));
                        return this;
                    };
                    CoreTask.prototype.ifThenElse = function (cond, thenSub, elseSub) {
                        if (cond == null) {
                            throw new Error("condition should not be null");
                        }
                        if (thenSub == null) {
                            throw new Error("thenSub should not be null");
                        }
                        if (elseSub == null) {
                            throw new Error("elseSub should not be null");
                        }
                        this.addAction(new org.mwg.core.task.ActionIfThenElse(cond, thenSub, elseSub));
                        return this;
                    };
                    CoreTask.prototype.whileDo = function (cond, then) {
                        this.addAction(new org.mwg.core.task.ActionWhileDo(cond, then));
                        return this;
                    };
                    CoreTask.prototype.doWhile = function (then, cond) {
                        this.addAction(new org.mwg.core.task.ActionDoWhile(then, cond));
                        return this;
                    };
                    CoreTask.prototype.then = function (p_action) {
                        if (p_action == null) {
                            throw new Error("action should not be null");
                        }
                        this.addAction(new org.mwg.core.task.ActionWrapper(p_action));
                        return this;
                    };
                    CoreTask.prototype.foreach = function (subTask) {
                        if (subTask == null) {
                            throw new Error("subTask should not be null");
                        }
                        this.addAction(new org.mwg.core.task.ActionForeach(subTask));
                        return this;
                    };
                    CoreTask.prototype.flatmap = function (subTask) {
                        if (subTask == null) {
                            throw new Error("subTask should not be null");
                        }
                        this.addAction(new org.mwg.core.task.ActionFlatmap(subTask));
                        return this;
                    };
                    CoreTask.prototype.foreachPar = function (subTask) {
                        if (subTask == null) {
                            throw new Error("subTask should not be null");
                        }
                        this.addAction(new org.mwg.core.task.ActionForeachPar(subTask));
                        return this;
                    };
                    CoreTask.prototype.flatmapPar = function (subTask) {
                        if (subTask == null) {
                            throw new Error("subTask should not be null");
                        }
                        this.addAction(new org.mwg.core.task.ActionFlatmapPar(subTask));
                        return this;
                    };
                    CoreTask.prototype.save = function () {
                        this.addAction(new org.mwg.core.task.ActionSave());
                        return this;
                    };
                    CoreTask.prototype.clear = function () {
                        this.addAction(new org.mwg.core.task.ActionClear());
                        return this;
                    };
                    CoreTask.prototype.lookup = function (nodeId) {
                        this.addAction(new org.mwg.core.task.ActionLookup(nodeId));
                        return this;
                    };
                    CoreTask.prototype.lookupAll = function (nodeId) {
                        this.addAction(new org.mwg.core.task.ActionLookupAll(nodeId));
                        return this;
                    };
                    CoreTask.prototype.execute = function (graph, callback) {
                        this.executeWith(graph, null, callback);
                    };
                    CoreTask.prototype.executeSync = function (graph) {
                        var waiter = graph.newSyncCounter(1);
                        this.executeWith(graph, null, waiter.wrap());
                        return waiter.waitResult();
                    };
                    CoreTask.prototype.executeWith = function (graph, initial, callback) {
                        var _this = this;
                        if (this._first != null) {
                            var initalRes = void 0;
                            if (initial instanceof org.mwg.core.task.CoreTaskResult) {
                                initalRes = initial.clone();
                            }
                            else {
                                initalRes = new org.mwg.core.task.CoreTaskResult(initial, true);
                            }
                            var hook = null;
                            if (this._hookFactory != null) {
                                hook = this._hookFactory.newHook();
                            }
                            else if (graph.taskHookFactory() != null) {
                                hook = graph.taskHookFactory().newHook();
                            }
                            var context_1 = new org.mwg.core.task.CoreTaskContext(null, initalRes, graph, hook, callback);
                            graph.scheduler().dispatch(org.mwg.plugin.SchedulerAffinity.SAME_THREAD, function () {
                                {
                                    context_1.execute(_this._first);
                                }
                            });
                        }
                        else {
                            if (callback != null) {
                                callback(this.emptyResult());
                            }
                        }
                    };
                    CoreTask.prototype.prepareWith = function (graph, initial, callback) {
                        var initalRes;
                        if (initial instanceof org.mwg.core.task.CoreTaskResult) {
                            initalRes = initial.clone();
                        }
                        else {
                            initalRes = new org.mwg.core.task.CoreTaskResult(initial, true);
                        }
                        var hook = null;
                        if (this._hookFactory != null) {
                            hook = this._hookFactory.newHook();
                        }
                        else if (graph.taskHookFactory() != null) {
                            hook = graph.taskHookFactory().newHook();
                        }
                        return new org.mwg.core.task.CoreTaskContext(null, initalRes, graph, hook, callback);
                    };
                    CoreTask.prototype.executeUsing = function (preparedContext) {
                        var _this = this;
                        if (this._first != null) {
                            preparedContext.graph().scheduler().dispatch(org.mwg.plugin.SchedulerAffinity.SAME_THREAD, function () {
                                {
                                    preparedContext.execute(_this._first);
                                }
                            });
                        }
                        else {
                            var casted = preparedContext;
                            if (casted._callback != null) {
                                casted._callback(this.emptyResult());
                            }
                        }
                    };
                    CoreTask.prototype.executeFrom = function (parentContext, initial, affinity, callback) {
                        var _this = this;
                        if (this._first != null) {
                            var context_2 = new org.mwg.core.task.CoreTaskContext(parentContext, initial.clone(), parentContext.graph(), parentContext.hook(), callback);
                            parentContext.graph().scheduler().dispatch(affinity, function () {
                                {
                                    context_2.execute(_this._first);
                                }
                            });
                        }
                        else {
                            if (callback != null) {
                                callback(this.emptyResult());
                            }
                        }
                    };
                    CoreTask.prototype.executeFromUsing = function (parentContext, initial, affinity, contextInitializer, callback) {
                        var _this = this;
                        if (this._first != null) {
                            var context_3 = new org.mwg.core.task.CoreTaskContext(parentContext, initial.clone(), parentContext.graph(), parentContext.hook(), callback);
                            if (contextInitializer != null) {
                                contextInitializer(context_3);
                            }
                            parentContext.graph().scheduler().dispatch(affinity, function () {
                                {
                                    context_3.execute(_this._first);
                                }
                            });
                        }
                        else {
                            if (callback != null) {
                                callback(this.emptyResult());
                            }
                        }
                    };
                    CoreTask.prototype.action = function (name, flatParams) {
                        if (name == null) {
                            throw new Error("name should not be null");
                        }
                        if (flatParams == null) {
                            throw new Error("flatParams should not be null");
                        }
                        this.addAction(new org.mwg.core.task.ActionPlugin(name, flatParams));
                        return this;
                    };
                    CoreTask.prototype.parse = function (flat) {
                        if (flat == null) {
                            throw new Error("flat should not be null");
                        }
                        var cursor = 0;
                        var flatSize = flat.length;
                        var previous = 0;
                        var actionName = null;
                        var isClosed = false;
                        var isEscaped = false;
                        while (cursor < flatSize) {
                            var current = flat.charAt(cursor);
                            switch (current) {
                                case '\'':
                                    isEscaped = true;
                                    while (cursor < flatSize) {
                                        if (flat.charAt(cursor) == '\'') {
                                            break;
                                        }
                                        cursor++;
                                    }
                                    break;
                                case org.mwg.Constants.TASK_SEP:
                                    if (!isClosed) {
                                        var getName = flat.substring(previous, cursor);
                                        this.action("get", getName);
                                    }
                                    actionName = null;
                                    isEscaped = false;
                                    previous = cursor + 1;
                                    break;
                                case org.mwg.Constants.TASK_PARAM_OPEN:
                                    actionName = flat.substring(previous, cursor);
                                    previous = cursor + 1;
                                    break;
                                case org.mwg.Constants.TASK_PARAM_CLOSE:
                                    var extracted = void 0;
                                    if (isEscaped) {
                                        extracted = flat.substring(previous + 1, cursor - 1);
                                    }
                                    else {
                                        extracted = flat.substring(previous, cursor);
                                    }
                                    this.action(actionName, extracted);
                                    actionName = null;
                                    previous = cursor + 1;
                                    isClosed = true;
                                    break;
                            }
                            cursor++;
                        }
                        if (!isClosed) {
                            var getName = flat.substring(previous, cursor);
                            if (getName.length > 0) {
                                this.action("get", getName);
                            }
                        }
                        return this;
                    };
                    CoreTask.prototype.newNode = function () {
                        this.addAction(new org.mwg.core.task.ActionNewNode(null));
                        return this;
                    };
                    CoreTask.prototype.newTypedNode = function (typeNode) {
                        this.addAction(new org.mwg.core.task.ActionNewNode(typeNode));
                        return this;
                    };
                    CoreTask.prototype.setProperty = function (propertyName, propertyType, variableNameToSet) {
                        if (propertyName == null) {
                            throw new Error("propertyName should not be null");
                        }
                        if (variableNameToSet == null) {
                            throw new Error("variableNameToSet should not be null");
                        }
                        this.addAction(new org.mwg.core.task.ActionSetProperty(propertyName, propertyType, variableNameToSet, false));
                        return this;
                    };
                    CoreTask.prototype.forceProperty = function (propertyName, propertyType, variableNameToSet) {
                        if (propertyName == null) {
                            throw new Error("propertyName should not be null");
                        }
                        if (variableNameToSet == null) {
                            throw new Error("variableNameToSet should not be null");
                        }
                        this.addAction(new org.mwg.core.task.ActionSetProperty(propertyName, propertyType, variableNameToSet, true));
                        return this;
                    };
                    CoreTask.prototype.removeProperty = function (propertyName) {
                        if (propertyName == null) {
                            throw new Error("propertyName should not be null");
                        }
                        this.addAction(new org.mwg.core.task.ActionRemoveProperty(propertyName));
                        return this;
                    };
                    CoreTask.prototype.add = function (relationName, variableNameToAdd) {
                        if (relationName == null) {
                            throw new Error("relationName should not be null");
                        }
                        if (variableNameToAdd == null) {
                            throw new Error("variableNameToAdd should not be null");
                        }
                        this.addAction(new org.mwg.core.task.ActionAdd(relationName, variableNameToAdd));
                        return this;
                    };
                    CoreTask.prototype.addTo = function (relationName, variableNameTarget) {
                        if (relationName == null) {
                            throw new Error("relationName should not be null");
                        }
                        if (variableNameTarget == null) {
                            throw new Error("variableNameTarget should not be null");
                        }
                        this.addAction(new org.mwg.core.task.ActionAddTo(relationName, variableNameTarget));
                        return this;
                    };
                    CoreTask.prototype.propertiesWithTypes = function (filter) {
                        this.addAction(new org.mwg.core.task.ActionProperties(filter));
                        return this;
                    };
                    CoreTask.prototype.properties = function () {
                        this.addAction(new org.mwg.core.task.ActionProperties(-1));
                        return this;
                    };
                    CoreTask.prototype.remove = function (relationName, variableNameToRemove) {
                        if (relationName == null) {
                            throw new Error("relationName should not be null");
                        }
                        if (variableNameToRemove == null) {
                            throw new Error("variableNameToRemove should not be null");
                        }
                        this.addAction(new org.mwg.core.task.ActionRemove(relationName, variableNameToRemove));
                        return this;
                    };
                    CoreTask.prototype.jump = function (time) {
                        if (time == null) {
                            throw new Error("time should not be null");
                        }
                        this.addAction(new org.mwg.core.task.ActionJump(time));
                        return this;
                    };
                    CoreTask.prototype.math = function (expression) {
                        this.addAction(new org.mwg.core.task.ActionMath(expression));
                        return this;
                    };
                    CoreTask.prototype.split = function (splitPattern) {
                        this.addAction(new org.mwg.core.task.ActionSplit(splitPattern));
                        return this;
                    };
                    CoreTask.prototype.loop = function (from, to, subTask) {
                        this.addAction(new org.mwg.core.task.ActionLoop(from, to, subTask));
                        return this;
                    };
                    CoreTask.prototype.loopPar = function (from, to, subTask) {
                        this.addAction(new org.mwg.core.task.ActionLoopPar(from, to, subTask));
                        return this;
                    };
                    CoreTask.prototype.print = function (name) {
                        this.addAction(new org.mwg.core.task.ActionPrint(name, false));
                        return this;
                    };
                    CoreTask.prototype.println = function (name) {
                        this.addAction(new org.mwg.core.task.ActionPrint(name, true));
                        return this;
                    };
                    CoreTask.prototype.hook = function (p_hookFactory) {
                        this._hookFactory = p_hookFactory;
                        return this;
                    };
                    CoreTask.prototype.emptyResult = function () {
                        return new org.mwg.core.task.CoreTaskResult(null, false);
                    };
                    CoreTask.prototype.mathConditional = function (mathExpression) {
                        return new org.mwg.core.task.math.MathConditional(mathExpression).conditional();
                    };
                    CoreTask.fillDefault = function (registry) {
                        registry.put("get", function (params) {
                            {
                                if (params.length != 1) {
                                    throw new Error("get action need one parameter");
                                }
                                return new org.mwg.core.task.ActionGet(params[0]);
                            }
                        });
                        registry.put("math", function (params) {
                            {
                                if (params.length != 1) {
                                    throw new Error("math action need one parameter");
                                }
                                return new org.mwg.core.task.ActionMath(params[0]);
                            }
                        });
                        registry.put("traverse", function (params) {
                            {
                                if (params.length != 1) {
                                    throw new Error("traverse action need one parameter");
                                }
                                return new org.mwg.core.task.ActionTraverse(params[0]);
                            }
                        });
                        registry.put("traverseOrKeep", function (params) {
                            {
                                if (params.length != 1) {
                                    throw new Error("traverseOrKeep action need one parameter");
                                }
                                return new org.mwg.core.task.ActionTraverseOrKeep(params[0]);
                            }
                        });
                        registry.put("fromIndexAll", function (params) {
                            {
                                if (params.length != 1) {
                                    throw new Error("fromIndexAll action need one parameter");
                                }
                                return new org.mwg.core.task.ActionFromIndexAll(params[0]);
                            }
                        });
                        registry.put("fromIndex", function (params) {
                            {
                                if (params.length != 2) {
                                    throw new Error("fromIndex action need two parameter");
                                }
                                return new org.mwg.core.task.ActionFromIndex(params[0], params[1]);
                            }
                        });
                        registry.put("with", function (params) {
                            {
                                if (params.length != 2) {
                                    throw new Error("with action need two parameter");
                                }
                                return new org.mwg.core.task.ActionWith(params[0], params[1]);
                            }
                        });
                        registry.put("without", function (params) {
                            {
                                if (params.length != 2) {
                                    throw new Error("without action need two parameter");
                                }
                                return new org.mwg.core.task.ActionWithout(params[0], params[1]);
                            }
                        });
                    };
                    return CoreTask;
                }());
                task.CoreTask = CoreTask;
                var CoreTaskContext = (function () {
                    function CoreTaskContext(parentContext, initial, p_graph, p_hook, p_callback) {
                        this._localVariables = null;
                        this._nextVariables = null;
                        this._hook = p_hook;
                        if (parentContext != null) {
                            this._time = parentContext.time();
                            this._world = parentContext.world();
                        }
                        else {
                            this._world = 0;
                            this._time = 0;
                        }
                        this._graph = p_graph;
                        this._parent = parentContext;
                        var castedParentContext = parentContext;
                        if (parentContext == null) {
                            this._globalVariables = new java.util.ConcurrentHashMap();
                        }
                        else {
                            this._globalVariables = castedParentContext.globalVariables();
                        }
                        this._result = initial;
                        this._callback = p_callback;
                    }
                    CoreTaskContext.prototype.graph = function () {
                        return this._graph;
                    };
                    CoreTaskContext.prototype.world = function () {
                        return this._world;
                    };
                    CoreTaskContext.prototype.setWorld = function (p_world) {
                        this._world = p_world;
                    };
                    CoreTaskContext.prototype.time = function () {
                        return this._time;
                    };
                    CoreTaskContext.prototype.setTime = function (p_time) {
                        this._time = p_time;
                    };
                    CoreTaskContext.prototype.variable = function (name) {
                        var resolved = this._globalVariables.get(name);
                        if (resolved == null) {
                            resolved = this.internal_deep_resolve(name);
                        }
                        return resolved;
                    };
                    CoreTaskContext.prototype.internal_deep_resolve = function (name) {
                        var resolved = null;
                        if (this._localVariables != null) {
                            resolved = this._localVariables.get(name);
                        }
                        if (resolved == null && this._parent != null) {
                            var castedParent = this._parent;
                            if (castedParent._nextVariables != null) {
                                resolved = castedParent._nextVariables.get(name);
                                if (resolved != null) {
                                    return resolved;
                                }
                            }
                            return castedParent.internal_deep_resolve(name);
                        }
                        else {
                            return resolved;
                        }
                    };
                    CoreTaskContext.prototype.wrap = function (input) {
                        return new org.mwg.core.task.CoreTaskResult(input, false);
                    };
                    CoreTaskContext.prototype.wrapClone = function (input) {
                        return new org.mwg.core.task.CoreTaskResult(input, true);
                    };
                    CoreTaskContext.prototype.newResult = function () {
                        return new org.mwg.core.task.CoreTaskResult(null, false);
                    };
                    CoreTaskContext.prototype.declareVariable = function (name) {
                        if (this._localVariables == null) {
                            this._localVariables = new java.util.HashMap();
                        }
                        this._localVariables.put(name, new org.mwg.core.task.CoreTaskResult(null, false));
                    };
                    CoreTaskContext.prototype.lazyWrap = function (input) {
                        if (input instanceof org.mwg.core.task.CoreTaskResult) {
                            return input;
                        }
                        else {
                            return this.wrap(input);
                        }
                    };
                    CoreTaskContext.prototype.defineVariable = function (name, initialResult) {
                        if (this._localVariables == null) {
                            this._localVariables = new java.util.HashMap();
                        }
                        this._localVariables.put(name, this.lazyWrap(initialResult).clone());
                    };
                    CoreTaskContext.prototype.defineVariableForSubTask = function (name, initialResult) {
                        if (this._nextVariables == null) {
                            this._nextVariables = new java.util.HashMap();
                        }
                        this._nextVariables.put(name, this.lazyWrap(initialResult).clone());
                    };
                    CoreTaskContext.prototype.setGlobalVariable = function (name, value) {
                        var previous = this._globalVariables.put(name, this.lazyWrap(value).clone());
                        if (previous != null) {
                            previous.free();
                        }
                    };
                    CoreTaskContext.prototype.setVariable = function (name, value) {
                        var target = this.internal_deep_resolve_map(name);
                        if (target == null) {
                            if (this._localVariables == null) {
                                this._localVariables = new java.util.HashMap();
                            }
                            target = this._localVariables;
                        }
                        var previous = target.put(name, this.lazyWrap(value).clone());
                        if (previous != null) {
                            previous.free();
                        }
                    };
                    CoreTaskContext.prototype.internal_deep_resolve_map = function (name) {
                        if (this._localVariables != null) {
                            var resolved = this._localVariables.get(name);
                            if (resolved != null) {
                                return this._localVariables;
                            }
                        }
                        if (this._parent != null) {
                            var castedParent = this._parent;
                            if (castedParent._nextVariables != null) {
                                var resolved = castedParent._nextVariables.get(name);
                                if (resolved != null) {
                                    return this._localVariables;
                                }
                            }
                            return this._parent.internal_deep_resolve_map(name);
                        }
                        else {
                            return null;
                        }
                    };
                    CoreTaskContext.prototype.addToGlobalVariable = function (name, value) {
                        var previous = this._globalVariables.get(name);
                        if (previous == null) {
                            previous = new org.mwg.core.task.CoreTaskResult(null, false);
                            this._globalVariables.put(name, previous);
                        }
                        if (value != null) {
                            if (value instanceof org.mwg.core.task.CoreTaskResult) {
                                var casted = value;
                                for (var i = 0; i < casted.size(); i++) {
                                    var loop = casted.get(i);
                                    if (loop instanceof org.mwg.plugin.AbstractNode) {
                                        var castedNode = loop;
                                        previous.add(castedNode.graph().cloneNode(castedNode));
                                    }
                                    else {
                                        previous.add(loop);
                                    }
                                }
                            }
                            else if (value instanceof org.mwg.plugin.AbstractNode) {
                                var castedNode = value;
                                previous.add(castedNode.graph().cloneNode(castedNode));
                            }
                            else {
                                previous.add(value);
                            }
                        }
                    };
                    CoreTaskContext.prototype.addToVariable = function (name, value) {
                        var target = this.internal_deep_resolve_map(name);
                        if (target == null) {
                            if (this._localVariables == null) {
                                this._localVariables = new java.util.HashMap();
                            }
                            target = this._localVariables;
                        }
                        var previous = target.get(name);
                        if (previous == null) {
                            previous = new org.mwg.core.task.CoreTaskResult(null, false);
                            target.put(name, previous);
                        }
                        if (value != null) {
                            if (value instanceof org.mwg.core.task.CoreTaskResult) {
                                var casted = value;
                                for (var i = 0; i < casted.size(); i++) {
                                    var loop = casted.get(i);
                                    if (loop instanceof org.mwg.plugin.AbstractNode) {
                                        var castedNode = loop;
                                        previous.add(castedNode.graph().cloneNode(castedNode));
                                    }
                                    else {
                                        previous.add(loop);
                                    }
                                }
                            }
                            else if (value instanceof org.mwg.plugin.AbstractNode) {
                                var castedNode = value;
                                previous.add(castedNode.graph().cloneNode(castedNode));
                            }
                            else {
                                previous.add(value);
                            }
                        }
                    };
                    CoreTaskContext.prototype.globalVariables = function () {
                        return this._globalVariables;
                    };
                    CoreTaskContext.prototype.nextVariables = function () {
                        return this._globalVariables;
                    };
                    CoreTaskContext.prototype.variables = function () {
                        return this._localVariables;
                    };
                    CoreTaskContext.prototype.result = function () {
                        return this._result;
                    };
                    CoreTaskContext.prototype.resultAsNodes = function () {
                        return this._result;
                    };
                    CoreTaskContext.prototype.resultAsStrings = function () {
                        return this._result;
                    };
                    CoreTaskContext.prototype.continueWith = function (nextResult) {
                        var previousResult = this._result;
                        if (previousResult != null && previousResult != nextResult) {
                            previousResult.free();
                        }
                        this._result = nextResult;
                        this.continueTask();
                    };
                    CoreTaskContext.prototype.continueTask = function () {
                        if (this._hook != null) {
                            this._hook.afterAction(this._current, this);
                        }
                        var nextAction = this._current.next();
                        this._current = nextAction;
                        if (nextAction == null) {
                            if (this._localVariables != null) {
                                var localValues = this._localVariables.keySet();
                                var flatLocalValues = localValues.toArray(new Array(localValues.size()));
                                for (var i = 0; i < flatLocalValues.length; i++) {
                                    this._localVariables.get(flatLocalValues[i]).free();
                                }
                            }
                            if (this._nextVariables != null) {
                                var nextValues = this._nextVariables.keySet();
                                var flatNextValues = nextValues.toArray(new Array(nextValues.size()));
                                for (var i = 0; i < flatNextValues.length; i++) {
                                    this._nextVariables.get(flatNextValues[i]).free();
                                }
                            }
                            if (this._parent == null) {
                                var globalValues = this._globalVariables.keySet();
                                var globalFlatValues = globalValues.toArray(new Array(globalValues.size()));
                                for (var i = 0; i < globalFlatValues.length; i++) {
                                    this._globalVariables.get(globalFlatValues[i]).free();
                                }
                            }
                            if (this._hook != null) {
                                if (this._parent == null) {
                                    this._hook.end(this);
                                }
                                else {
                                    this._hook.afterTask(this);
                                }
                            }
                            if (this._callback != null) {
                                this._callback(this._result);
                            }
                            else {
                                if (this._result != null) {
                                    this._result.free();
                                }
                            }
                        }
                        else {
                            if (this._hook != null) {
                                this._hook.beforeAction(nextAction, this);
                            }
                            nextAction.eval(this);
                        }
                    };
                    CoreTaskContext.prototype.execute = function (initialTaskAction) {
                        this._current = initialTaskAction;
                        if (this._hook != null) {
                            if (this._parent == null) {
                                this._hook.start(this);
                            }
                            else {
                                this._hook.beforeTask(this._parent, this);
                            }
                            this._hook.beforeAction(this._current, this);
                        }
                        this._current.eval(this);
                    };
                    CoreTaskContext.prototype.template = function (input) {
                        if (input == null) {
                            return null;
                        }
                        var cursor = 0;
                        var buffer = null;
                        var previousPos = -1;
                        while (cursor < input.length) {
                            var currentChar = input.charAt(cursor);
                            var previousChar = '0';
                            var nextChar = '0';
                            if (cursor > 0) {
                                previousChar = input.charAt(cursor - 1);
                            }
                            if (cursor + 1 < input.length) {
                                nextChar = input.charAt(cursor + 1);
                            }
                            if (currentChar == '{' && previousChar == '{') {
                                previousPos = cursor + 1;
                            }
                            else if (previousPos != -1 && currentChar == '}' && previousChar == '}') {
                                if (buffer == null) {
                                    buffer = new java.lang.StringBuilder();
                                    buffer.append(input.substring(0, previousPos - 2));
                                }
                                var contextKey = input.substring(previousPos, cursor - 1).trim();
                                if (contextKey.length > 0 && contextKey.charAt(0) == '=') {
                                    var mathEngine = org.mwg.core.task.math.CoreMathExpressionEngine.parse(contextKey.substring(1));
                                    var value = mathEngine.eval(null, this, new java.util.HashMap());
                                    var valueStr = value + "";
                                    for (var i = valueStr.length - 1; i >= 0; i--) {
                                        if (valueStr.charAt(i) == '.') {
                                            valueStr = valueStr.substring(0, i);
                                            break;
                                        }
                                        else if (valueStr.charAt(i) != '0') {
                                            break;
                                        }
                                    }
                                    buffer.append(valueStr);
                                }
                                else {
                                    var indexArray = -1;
                                    if (contextKey.charAt(contextKey.length - 1) == ']') {
                                        var indexStart = -1;
                                        for (var i = contextKey.length - 3; i >= 0; i--) {
                                            if (contextKey.charAt(i) == '[') {
                                                indexStart = i + 1;
                                                break;
                                            }
                                        }
                                        if (indexStart != -1) {
                                            indexArray = org.mwg.core.task.TaskHelper.parseInt(contextKey.substring(indexStart, contextKey.length - 1));
                                            contextKey = contextKey.substring(0, indexStart - 1);
                                            if (indexArray < 0) {
                                                throw new Error("Array index out of range: " + indexArray);
                                            }
                                        }
                                    }
                                    var foundVar = this.variable(contextKey);
                                    if (foundVar == null && contextKey === "result") {
                                        foundVar = this.result();
                                    }
                                    if (foundVar != null) {
                                        if (foundVar.size() == 1 || indexArray != -1) {
                                            var toShow = null;
                                            if (indexArray == -1) {
                                                toShow = foundVar.get(0);
                                            }
                                            else {
                                                toShow = foundVar.get(indexArray);
                                            }
                                            buffer.append(toShow);
                                        }
                                        else {
                                            var it = foundVar.iterator();
                                            buffer.append("[");
                                            var isFirst = true;
                                            var next = it.next();
                                            while (next != null) {
                                                if (isFirst) {
                                                    isFirst = false;
                                                }
                                                else {
                                                    buffer.append(",");
                                                }
                                                buffer.append(next);
                                                next = it.next();
                                            }
                                            buffer.append("]");
                                        }
                                    }
                                }
                                previousPos = -1;
                            }
                            else {
                                if (previousPos == -1 && buffer != null) {
                                    if (currentChar == '{' && nextChar == '{') {
                                    }
                                    else {
                                        buffer.append(input.charAt(cursor));
                                    }
                                }
                            }
                            cursor++;
                        }
                        if (buffer == null) {
                            return input;
                        }
                        else {
                            return buffer.toString();
                        }
                    };
                    CoreTaskContext.prototype.hook = function () {
                        return this._hook;
                    };
                    CoreTaskContext.prototype.toString = function () {
                        return "{result:" + this._result.toString() + "}";
                    };
                    return CoreTaskContext;
                }());
                task.CoreTaskContext = CoreTaskContext;
                var CoreTaskResult = (function () {
                    function CoreTaskResult(toWrap, protect) {
                        this._capacity = 0;
                        this._size = 0;
                        if (Array.isArray(toWrap)) {
                            var castedToWrap = toWrap;
                            this._size = toWrap.length;
                            this._capacity = this._size;
                            this._backend = new Array(this._size);
                            if (protect) {
                                for (var i = 0; i < this._size; i++) {
                                    var loopObj = castedToWrap[i];
                                    if (loopObj instanceof org.mwg.plugin.AbstractNode) {
                                        var loopNode = loopObj;
                                        this._backend[i] = loopNode.graph().cloneNode(loopNode);
                                    }
                                    else {
                                        this._backend[i] = loopObj;
                                    }
                                }
                            }
                            else {
                                java.lang.System.arraycopy(castedToWrap, 0, this._backend, 0, this._size);
                            }
                        }
                        else if (toWrap instanceof Float64Array) {
                            var castedOther = toWrap;
                            this._backend = new Array(castedOther.length);
                            for (var i = 0; i < castedOther.length; i++) {
                                this._backend[i] = castedOther[i];
                            }
                            this._capacity = this._backend.length;
                            this._size = this._capacity;
                        }
                        else if (toWrap instanceof Int32Array) {
                            var castedOther = toWrap;
                            this._backend = new Array(castedOther.length);
                            for (var i = 0; i < castedOther.length; i++) {
                                this._backend[i] = castedOther[i];
                            }
                            this._capacity = this._backend.length;
                            this._size = this._capacity;
                        }
                        else if (toWrap instanceof Float64Array) {
                            var castedOther = toWrap;
                            this._backend = new Array(castedOther.length);
                            for (var i = 0; i < castedOther.length; i++) {
                                this._backend[i] = castedOther[i];
                            }
                            this._capacity = this._backend.length;
                            this._size = this._capacity;
                        }
                        else if (toWrap instanceof java.util.ArrayList) {
                            var castedOtherList = toWrap;
                            this._backend = new Array(castedOtherList.size());
                            for (var i = 0; i < castedOtherList.size(); i++) {
                                this._backend[i] = castedOtherList.get(i);
                            }
                            this._capacity = this._backend.length;
                            this._size = this._capacity;
                        }
                        else if (toWrap instanceof org.mwg.core.task.CoreTaskResult) {
                            var other = toWrap;
                            this._size = other._size;
                            this._capacity = other._capacity;
                            if (other._backend != null) {
                                this._backend = new Array(other._backend.length);
                                if (protect) {
                                    for (var i = 0; i < this._size; i++) {
                                        var loopObj = other._backend[i];
                                        if (loopObj instanceof org.mwg.plugin.AbstractNode) {
                                            var loopNode = loopObj;
                                            this._backend[i] = loopNode.graph().cloneNode(loopNode);
                                        }
                                        else {
                                            this._backend[i] = loopObj;
                                        }
                                    }
                                }
                                else {
                                    java.lang.System.arraycopy(other._backend, 0, this._backend, 0, this._size);
                                }
                            }
                        }
                        else {
                            if (toWrap != null) {
                                this._backend = new Array(1);
                                this._capacity = 1;
                                this._size = 1;
                                if (toWrap instanceof org.mwg.plugin.AbstractNode) {
                                    var toWrapNode = toWrap;
                                    if (protect) {
                                        this._backend[0] = toWrapNode.graph().cloneNode(toWrapNode);
                                    }
                                    else {
                                        this._backend[0] = toWrapNode;
                                    }
                                }
                                else {
                                    this._backend[0] = toWrap;
                                }
                            }
                        }
                    }
                    CoreTaskResult.prototype.asArray = function () {
                        var flat = new Array(this._size);
                        if (this._backend != null) {
                            java.lang.System.arraycopy(this._backend, 0, flat, 0, this._size);
                        }
                        return flat;
                    };
                    CoreTaskResult.prototype.iterator = function () {
                        return new org.mwg.core.task.CoreTaskResultIterator(this._backend);
                    };
                    CoreTaskResult.prototype.get = function (index) {
                        if (index < this._size) {
                            return this._backend[index];
                        }
                        else {
                            return null;
                        }
                    };
                    CoreTaskResult.prototype.set = function (index, input) {
                        if (index >= this._capacity) {
                            this.extendTil(index);
                        }
                        this._backend[index] = input;
                        if (index >= this._size) {
                            this._size++;
                        }
                    };
                    CoreTaskResult.prototype.allocate = function (index) {
                        if (index >= this._capacity) {
                            if (this._backend == null) {
                                this._backend = new Array(index);
                                this._capacity = index;
                            }
                            else {
                                throw new Error("Not implemented yet!!!");
                            }
                        }
                    };
                    CoreTaskResult.prototype.add = function (input) {
                        if (this._size >= this._capacity) {
                            this.extendTil(this._size);
                        }
                        this.set(this._size, input);
                    };
                    CoreTaskResult.prototype.clear = function () {
                        this._backend = null;
                        this._capacity = 0;
                        this._size = 0;
                    };
                    CoreTaskResult.prototype.clone = function () {
                        return new org.mwg.core.task.CoreTaskResult(this, true);
                    };
                    CoreTaskResult.prototype.free = function () {
                        for (var i = 0; i < this._capacity; i++) {
                            if (this._backend[i] instanceof org.mwg.plugin.AbstractNode) {
                                this._backend[i].free();
                            }
                        }
                    };
                    CoreTaskResult.prototype.size = function () {
                        return this._size;
                    };
                    CoreTaskResult.prototype.extendTil = function (index) {
                        if (this._capacity <= index) {
                            var newCapacity = this._capacity * 2;
                            if (newCapacity <= index) {
                                newCapacity = index + 1;
                            }
                            var extendedBackend = new Array(newCapacity);
                            if (this._backend != null) {
                                java.lang.System.arraycopy(this._backend, 0, extendedBackend, 0, this._size);
                            }
                            this._backend = extendedBackend;
                            this._capacity = newCapacity;
                        }
                    };
                    CoreTaskResult.prototype.toString = function () {
                        return this.toJson(true);
                    };
                    CoreTaskResult.prototype.toJson = function (withContent) {
                        var builder = new java.lang.StringBuilder();
                        builder.append("[");
                        for (var i = 0; i < this._size; i++) {
                            if (i != 0) {
                                builder.append(",");
                            }
                            var loop = this._backend[i];
                            if (loop != null) {
                                builder.append(loop.toString());
                            }
                        }
                        builder.append("]");
                        return builder.toString();
                    };
                    return CoreTaskResult;
                }());
                task.CoreTaskResult = CoreTaskResult;
                var CoreTaskResultIterator = (function () {
                    function CoreTaskResultIterator(p_backend) {
                        this._current = new java.util.concurrent.atomic.AtomicInteger(0);
                        if (p_backend != null) {
                            this._backend = p_backend;
                        }
                        else {
                            this._backend = new Array(0);
                        }
                        this._size = this._backend.length;
                    }
                    CoreTaskResultIterator.prototype.next = function () {
                        var cursor = this._current.getAndIncrement();
                        if (cursor < this._size) {
                            return this._backend[cursor];
                        }
                        else {
                            return null;
                        }
                    };
                    CoreTaskResultIterator.prototype.nextWithIndex = function () {
                        var cursor = this._current.getAndIncrement();
                        if (cursor < this._size) {
                            if (this._backend[cursor] != null) {
                                return new org.mwg.utility.Tuple(cursor, this._backend[cursor]);
                            }
                            else {
                                return null;
                            }
                        }
                        else {
                            return null;
                        }
                    };
                    return CoreTaskResultIterator;
                }());
                task.CoreTaskResultIterator = CoreTaskResultIterator;
                var TaskHelper = (function () {
                    function TaskHelper() {
                    }
                    TaskHelper.flatNodes = function (toFLat, strict) {
                        if (toFLat instanceof org.mwg.plugin.AbstractNode) {
                            return [toFLat];
                        }
                        if (Array.isArray(toFLat)) {
                            var resAsArray = toFLat;
                            var nodes = new Array(0);
                            for (var i = 0; i < resAsArray.length; i++) {
                                if (resAsArray[i] instanceof org.mwg.plugin.AbstractNode) {
                                    var tmp = new Array(nodes.length + 1);
                                    java.lang.System.arraycopy(nodes, 0, tmp, 0, nodes.length);
                                    tmp[nodes.length] = resAsArray[i];
                                    nodes = tmp;
                                }
                                else if (Array.isArray(resAsArray[i])) {
                                    var innerNodes = org.mwg.core.task.TaskHelper.flatNodes(resAsArray[i], strict);
                                    var tmp = new Array(nodes.length + innerNodes.length);
                                    java.lang.System.arraycopy(nodes, 0, tmp, 0, nodes.length);
                                    java.lang.System.arraycopy(innerNodes, 0, tmp, nodes.length, innerNodes.length);
                                    nodes = tmp;
                                }
                                else if (strict) {
                                    throw new Error("[ActionIndexOrUnindexNode] The array in result contains an element with wrong type. " + "Expected type: AbstractNode. Actual type: " + resAsArray[i]);
                                }
                            }
                            return nodes;
                        }
                        else if (strict) {
                            throw new Error("[ActionIndexOrUnindexNode] Wrong type of result. Expected type is AbstractNode or an array of AbstractNode." + "Actual type is " + toFLat);
                        }
                        return new Array(0);
                    };
                    TaskHelper.parseInt = function (s) {
                        return parseInt(s);
                    };
                    return TaskHelper;
                }());
                task.TaskHelper = TaskHelper;
                var math;
                (function (math) {
                    var CoreMathExpressionEngine = (function () {
                        function CoreMathExpressionEngine(expression) {
                            this._cacheAST = this.buildAST(this.shuntingYard(expression));
                        }
                        CoreMathExpressionEngine.parse = function (p_expression) {
                            return new org.mwg.core.task.math.CoreMathExpressionEngine(p_expression);
                        };
                        CoreMathExpressionEngine.isNumber = function (st) {
                            return !isNaN(+st);
                        };
                        CoreMathExpressionEngine.isDigit = function (c) {
                            var cc = c.charCodeAt(0);
                            if (cc >= 0x30 && cc <= 0x39) {
                                return true;
                            }
                            return false;
                        };
                        CoreMathExpressionEngine.isLetter = function (c) {
                            var cc = c.charCodeAt(0);
                            if ((cc >= 0x41 && cc <= 0x5A) || (cc >= 0x61 && cc <= 0x7A)) {
                                return true;
                            }
                            return false;
                        };
                        CoreMathExpressionEngine.isWhitespace = function (c) {
                            var cc = c.charCodeAt(0);
                            if ((cc >= 0x0009 && cc <= 0x000D) || (cc == 0x0020) || (cc == 0x0085) || (cc == 0x00A0)) {
                                return true;
                            }
                            return false;
                        };
                        CoreMathExpressionEngine.prototype.shuntingYard = function (expression) {
                            var outputQueue = new java.util.ArrayList();
                            var stack = new java.util.Stack();
                            var tokenizer = new org.mwg.core.task.math.MathExpressionTokenizer(expression);
                            var lastFunction = null;
                            var previousToken = null;
                            while (tokenizer.hasNext()) {
                                var token = tokenizer.next();
                                if (org.mwg.core.task.math.MathEntities.getINSTANCE().functions.keySet().contains(token.toUpperCase())) {
                                    stack.push(token);
                                    lastFunction = token;
                                }
                                else if ("," === token) {
                                    while (!stack.isEmpty() && !("(" === stack.peek())) {
                                        outputQueue.add(stack.pop());
                                    }
                                    if (stack.isEmpty()) {
                                        throw new Error("Parse error for function '" + lastFunction + "'");
                                    }
                                }
                                else if (org.mwg.core.task.math.MathEntities.getINSTANCE().operators.keySet().contains(token)) {
                                    var o1 = org.mwg.core.task.math.MathEntities.getINSTANCE().operators.get(token);
                                    var token2 = stack.isEmpty() ? null : stack.peek();
                                    while (org.mwg.core.task.math.MathEntities.getINSTANCE().operators.keySet().contains(token2) && ((o1.isLeftAssoc() && o1.getPrecedence() <= org.mwg.core.task.math.MathEntities.getINSTANCE().operators.get(token2).getPrecedence()) || (o1.getPrecedence() < org.mwg.core.task.math.MathEntities.getINSTANCE().operators.get(token2).getPrecedence()))) {
                                        outputQueue.add(stack.pop());
                                        token2 = stack.isEmpty() ? null : stack.peek();
                                    }
                                    stack.push(token);
                                }
                                else if ("(" === token) {
                                    if (previousToken != null) {
                                        if (org.mwg.core.task.math.CoreMathExpressionEngine.isNumber(previousToken)) {
                                            throw new Error("Missing operator at character position " + tokenizer.getPos());
                                        }
                                    }
                                    stack.push(token);
                                }
                                else if (")" === token) {
                                    while (!stack.isEmpty() && !("(" === stack.peek())) {
                                        outputQueue.add(stack.pop());
                                    }
                                    if (stack.isEmpty()) {
                                        throw new Error("Mismatched parentheses");
                                    }
                                    stack.pop();
                                    if (!stack.isEmpty() && org.mwg.core.task.math.MathEntities.getINSTANCE().functions.keySet().contains(stack.peek().toUpperCase())) {
                                        outputQueue.add(stack.pop());
                                    }
                                }
                                else {
                                    outputQueue.add(token);
                                }
                                previousToken = token;
                            }
                            while (!stack.isEmpty()) {
                                var element = stack.pop();
                                if ("(" === element || ")" === element) {
                                    throw new Error("Mismatched parentheses");
                                }
                                outputQueue.add(element);
                            }
                            return outputQueue;
                        };
                        CoreMathExpressionEngine.prototype.eval = function (context, taskContext, variables) {
                            if (this._cacheAST == null) {
                                throw new Error("Call parse before");
                            }
                            var stack = new java.util.Stack();
                            for (var ii = 0; ii < this._cacheAST.length; ii++) {
                                var mathToken = this._cacheAST[ii];
                                switch (mathToken.type()) {
                                    case 0:
                                        var v1 = stack.pop();
                                        var v2 = stack.pop();
                                        var castedOp = mathToken;
                                        stack.push(castedOp.eval(v2, v1));
                                        break;
                                    case 1:
                                        var castedFunction = mathToken;
                                        var p = new Float64Array(castedFunction.getNumParams());
                                        for (var i = castedFunction.getNumParams() - 1; i >= 0; i--) {
                                            p[i] = stack.pop();
                                        }
                                        stack.push(castedFunction.eval(p));
                                        break;
                                    case 2:
                                        var castedDouble = mathToken;
                                        stack.push(castedDouble.content());
                                        break;
                                    case 3:
                                        var castedFreeToken = mathToken;
                                        var resolvedVar = null;
                                        if (variables != null) {
                                            resolvedVar = variables.get(castedFreeToken.content());
                                        }
                                        if (resolvedVar != null) {
                                            stack.push(resolvedVar);
                                        }
                                        else {
                                            if ("TIME" === castedFreeToken.content()) {
                                                stack.push(context.time());
                                            }
                                            else {
                                                var tokenName = castedFreeToken.content().trim();
                                                var resolved = null;
                                                var cleanName = null;
                                                if (context != null) {
                                                    if (tokenName.length > 0 && tokenName.charAt(0) == '{' && tokenName.charAt(tokenName.length - 1) == '}') {
                                                        resolved = context.get(castedFreeToken.content().substring(1, tokenName.length - 1));
                                                        cleanName = castedFreeToken.content().substring(1, tokenName.length - 1);
                                                    }
                                                    else {
                                                        resolved = context.get(castedFreeToken.content());
                                                        cleanName = castedFreeToken.content();
                                                    }
                                                    if (cleanName.length > 0 && cleanName.charAt(0) == '$') {
                                                        cleanName = cleanName.substring(1);
                                                    }
                                                }
                                                if (taskContext != null) {
                                                    if (resolved == null) {
                                                        if (tokenName.charAt(tokenName.length - 1) == ']') {
                                                            var indexStart = -1;
                                                            var indexArray = -1;
                                                            for (var i = tokenName.length - 3; i >= 0; i--) {
                                                                if (tokenName.charAt(i) == '[') {
                                                                    indexStart = i + 1;
                                                                    break;
                                                                }
                                                            }
                                                            if (indexStart != -1) {
                                                                indexArray = this.parseInt(tokenName.substring(indexStart, tokenName.length - 1));
                                                                tokenName = tokenName.substring(0, indexStart - 1);
                                                            }
                                                            var varRes = taskContext.variable(tokenName);
                                                            if (varRes == null && tokenName === "result") {
                                                                varRes = taskContext.result();
                                                            }
                                                            if (varRes != null && varRes.size() > indexArray) {
                                                                resolved = varRes.get(indexArray);
                                                            }
                                                        }
                                                        else {
                                                            var varRes = taskContext.variable(tokenName);
                                                            if (varRes == null && tokenName === "result") {
                                                                varRes = taskContext.result();
                                                            }
                                                            if (varRes != null) {
                                                                resolved = varRes.get(0);
                                                            }
                                                        }
                                                    }
                                                }
                                                if (resolved != null) {
                                                    var resultAsDouble = this.parseDouble(resolved.toString());
                                                    variables.put(cleanName, resultAsDouble);
                                                    var valueString = resolved.toString();
                                                    if (valueString === "true") {
                                                        stack.push(1.0);
                                                    }
                                                    else if (valueString === "false") {
                                                        stack.push(0.0);
                                                    }
                                                    else {
                                                        try {
                                                            stack.push(resultAsDouble);
                                                        }
                                                        catch ($ex$) {
                                                            if ($ex$ instanceof Error) {
                                                                var e = $ex$;
                                                                {
                                                                }
                                                            }
                                                            else {
                                                                throw $ex$;
                                                            }
                                                        }
                                                    }
                                                }
                                                else {
                                                    throw new Error("Unknow variable for name " + castedFreeToken.content());
                                                }
                                            }
                                        }
                                        break;
                                }
                            }
                            var result = stack.pop();
                            if (result == null) {
                                return 0;
                            }
                            else {
                                return result;
                            }
                        };
                        CoreMathExpressionEngine.prototype.buildAST = function (rpn) {
                            var result = new Array(rpn.size());
                            for (var ii = 0; ii < rpn.size(); ii++) {
                                var token = rpn.get(ii);
                                if (org.mwg.core.task.math.MathEntities.getINSTANCE().operators.keySet().contains(token)) {
                                    result[ii] = org.mwg.core.task.math.MathEntities.getINSTANCE().operators.get(token);
                                }
                                else if (org.mwg.core.task.math.MathEntities.getINSTANCE().functions.keySet().contains(token.toUpperCase())) {
                                    result[ii] = org.mwg.core.task.math.MathEntities.getINSTANCE().functions.get(token.toUpperCase());
                                }
                                else {
                                    if (token.length > 0 && org.mwg.core.task.math.CoreMathExpressionEngine.isLetter(token.charAt(0))) {
                                        result[ii] = new org.mwg.core.task.math.MathFreeToken(token);
                                    }
                                    else {
                                        try {
                                            var parsed = this.parseDouble(token);
                                            result[ii] = new org.mwg.core.task.math.MathDoubleToken(parsed);
                                        }
                                        catch ($ex$) {
                                            if ($ex$ instanceof Error) {
                                                var e = $ex$;
                                                {
                                                    result[ii] = new org.mwg.core.task.math.MathFreeToken(token);
                                                }
                                            }
                                            else {
                                                throw $ex$;
                                            }
                                        }
                                    }
                                }
                            }
                            return result;
                        };
                        CoreMathExpressionEngine.prototype.parseDouble = function (val) {
                            return parseFloat(val);
                        };
                        CoreMathExpressionEngine.prototype.parseInt = function (val) {
                            return parseInt(val);
                        };
                        CoreMathExpressionEngine.decimalSeparator = '.';
                        CoreMathExpressionEngine.minusSign = '-';
                        return CoreMathExpressionEngine;
                    }());
                    math.CoreMathExpressionEngine = CoreMathExpressionEngine;
                    var MathConditional = (function () {
                        function MathConditional(mathExpression) {
                            this._expression = mathExpression;
                            this._engine = org.mwg.core.task.math.CoreMathExpressionEngine.parse(mathExpression);
                        }
                        MathConditional.prototype.conditional = function () {
                            var _this = this;
                            return function (context) {
                                {
                                    var variables = new java.util.HashMap();
                                    variables.put("PI", Math.PI);
                                    variables.put("TRUE", 1.0);
                                    variables.put("FALSE", 0.0);
                                    return (_this._engine.eval(null, context, variables) >= 0.5);
                                }
                            };
                        };
                        MathConditional.prototype.toString = function () {
                            return "cond(\'" + this._expression + "\')";
                        };
                        return MathConditional;
                    }());
                    math.MathConditional = MathConditional;
                    var MathDoubleToken = (function () {
                        function MathDoubleToken(_content) {
                            this._content = _content;
                        }
                        MathDoubleToken.prototype.type = function () {
                            return 2;
                        };
                        MathDoubleToken.prototype.content = function () {
                            return this._content;
                        };
                        return MathDoubleToken;
                    }());
                    math.MathDoubleToken = MathDoubleToken;
                    var MathEntities = (function () {
                        function MathEntities() {
                            this.operators = new java.util.HashMap();
                            this.operators.put("+", new org.mwg.core.task.math.MathOperation("+", 20, true));
                            this.operators.put("-", new org.mwg.core.task.math.MathOperation("-", 20, true));
                            this.operators.put("*", new org.mwg.core.task.math.MathOperation("*", 30, true));
                            this.operators.put("/", new org.mwg.core.task.math.MathOperation("/", 30, true));
                            this.operators.put("%", new org.mwg.core.task.math.MathOperation("%", 30, true));
                            this.operators.put("^", new org.mwg.core.task.math.MathOperation("^", 40, false));
                            this.operators.put("&&", new org.mwg.core.task.math.MathOperation("&&", 4, false));
                            this.operators.put("||", new org.mwg.core.task.math.MathOperation("||", 2, false));
                            this.operators.put(">", new org.mwg.core.task.math.MathOperation(">", 10, false));
                            this.operators.put(">=", new org.mwg.core.task.math.MathOperation(">=", 10, false));
                            this.operators.put("<", new org.mwg.core.task.math.MathOperation("<", 10, false));
                            this.operators.put("<=", new org.mwg.core.task.math.MathOperation("<=", 10, false));
                            this.operators.put("==", new org.mwg.core.task.math.MathOperation("==", 7, false));
                            this.operators.put("!=", new org.mwg.core.task.math.MathOperation("!=", 7, false));
                            this.functions = new java.util.HashMap();
                            this.functions.put("NOT", new org.mwg.core.task.math.MathFunction("NOT", 1));
                            this.functions.put("IF", new org.mwg.core.task.math.MathFunction("IF", 3));
                            this.functions.put("RAND", new org.mwg.core.task.math.MathFunction("RAND", 0));
                            this.functions.put("SIN", new org.mwg.core.task.math.MathFunction("SIN", 1));
                            this.functions.put("COS", new org.mwg.core.task.math.MathFunction("COS", 1));
                            this.functions.put("TAN", new org.mwg.core.task.math.MathFunction("TAN", 1));
                            this.functions.put("ASIN", new org.mwg.core.task.math.MathFunction("ASIN", 1));
                            this.functions.put("ACOS", new org.mwg.core.task.math.MathFunction("ACOS", 1));
                            this.functions.put("ATAN", new org.mwg.core.task.math.MathFunction("ATAN", 1));
                            this.functions.put("MAX", new org.mwg.core.task.math.MathFunction("MAX", 2));
                            this.functions.put("MIN", new org.mwg.core.task.math.MathFunction("MIN", 2));
                            this.functions.put("ABS", new org.mwg.core.task.math.MathFunction("ABS", 1));
                            this.functions.put("LOG", new org.mwg.core.task.math.MathFunction("LOG", 1));
                            this.functions.put("ROUND", new org.mwg.core.task.math.MathFunction("ROUND", 2));
                            this.functions.put("FLOOR", new org.mwg.core.task.math.MathFunction("FLOOR", 1));
                            this.functions.put("CEILING", new org.mwg.core.task.math.MathFunction("CEILING", 1));
                            this.functions.put("SQRT", new org.mwg.core.task.math.MathFunction("SQRT", 1));
                            this.functions.put("SECONDS", new org.mwg.core.task.math.MathFunction("SECONDS", 1));
                            this.functions.put("MINUTES", new org.mwg.core.task.math.MathFunction("MINUTES", 1));
                            this.functions.put("HOURS", new org.mwg.core.task.math.MathFunction("HOURS", 1));
                            this.functions.put("DAY", new org.mwg.core.task.math.MathFunction("DAY", 1));
                            this.functions.put("MONTH", new org.mwg.core.task.math.MathFunction("MONTH", 1));
                            this.functions.put("YEAR", new org.mwg.core.task.math.MathFunction("YEAR", 1));
                            this.functions.put("DAYOFWEEK", new org.mwg.core.task.math.MathFunction("DAYOFWEEK", 1));
                        }
                        MathEntities.getINSTANCE = function () {
                            if (MathEntities.INSTANCE == null) {
                                MathEntities.INSTANCE = new org.mwg.core.task.math.MathEntities();
                            }
                            return MathEntities.INSTANCE;
                        };
                        MathEntities.INSTANCE = null;
                        return MathEntities;
                    }());
                    math.MathEntities = MathEntities;
                    var MathExpressionTokenizer = (function () {
                        function MathExpressionTokenizer(input) {
                            this.pos = 0;
                            this.input = input.trim();
                        }
                        MathExpressionTokenizer.prototype.hasNext = function () {
                            return (this.pos < this.input.length);
                        };
                        MathExpressionTokenizer.prototype.peekNextChar = function () {
                            if (this.pos < (this.input.length - 1)) {
                                return this.input.charAt(this.pos + 1);
                            }
                            else {
                                return '\0';
                            }
                        };
                        MathExpressionTokenizer.prototype.next = function () {
                            var token = new java.lang.StringBuilder();
                            if (this.pos >= this.input.length) {
                                return this.previousToken = null;
                            }
                            var ch = this.input.charAt(this.pos);
                            while (org.mwg.core.task.math.CoreMathExpressionEngine.isWhitespace(ch) && this.pos < this.input.length) {
                                ch = this.input.charAt(++this.pos);
                            }
                            if (org.mwg.core.task.math.CoreMathExpressionEngine.isDigit(ch)) {
                                while ((org.mwg.core.task.math.CoreMathExpressionEngine.isDigit(ch) || ch == org.mwg.core.task.math.CoreMathExpressionEngine.decimalSeparator) && (this.pos < this.input.length)) {
                                    token.append(this.input.charAt(this.pos++));
                                    ch = this.pos == this.input.length ? '\0' : this.input.charAt(this.pos);
                                }
                            }
                            else if (ch == org.mwg.core.task.math.CoreMathExpressionEngine.minusSign && org.mwg.core.task.math.CoreMathExpressionEngine.isDigit(this.peekNextChar()) && ("(" === this.previousToken || "," === this.previousToken || this.previousToken == null || org.mwg.core.task.math.MathEntities.getINSTANCE().operators.keySet().contains(this.previousToken))) {
                                token.append(org.mwg.core.task.math.CoreMathExpressionEngine.minusSign);
                                this.pos++;
                                token.append(this.next());
                            }
                            else if (org.mwg.core.task.math.CoreMathExpressionEngine.isLetter(ch) || (ch == '_') || (ch == '{') || (ch == '}') || (ch == '$')) {
                                while ((org.mwg.core.task.math.CoreMathExpressionEngine.isLetter(ch) || org.mwg.core.task.math.CoreMathExpressionEngine.isDigit(ch) || (ch == '_') || (ch == '{') || (ch == '}') || (ch == '$')) && (this.pos < this.input.length)) {
                                    token.append(this.input.charAt(this.pos++));
                                    ch = this.pos == this.input.length ? '\0' : this.input.charAt(this.pos);
                                }
                                if (this.pos < this.input.length) {
                                    if (this.input.charAt(this.pos) == '[') {
                                        token.append(this.input.charAt(this.pos++));
                                        ch = this.pos == this.input.length ? '\0' : this.input.charAt(this.pos);
                                        while (org.mwg.core.task.math.CoreMathExpressionEngine.isDigit(ch) && this.pos < this.input.length) {
                                            token.append(this.input.charAt(this.pos++));
                                            ch = this.pos == this.input.length ? '\0' : this.input.charAt(this.pos);
                                        }
                                        if (this.input.charAt(this.pos) != ']') {
                                            throw new Error("Error in array definition '" + token + "' at position " + (this.pos - token.length + 1));
                                        }
                                        else {
                                            token.append(this.input.charAt(this.pos++));
                                        }
                                    }
                                }
                            }
                            else if (ch == '(' || ch == ')' || ch == ',') {
                                token.append(ch);
                                this.pos++;
                            }
                            else {
                                while (!org.mwg.core.task.math.CoreMathExpressionEngine.isLetter(ch) && !org.mwg.core.task.math.CoreMathExpressionEngine.isDigit(ch) && ch != '_' && !org.mwg.core.task.math.CoreMathExpressionEngine.isWhitespace(ch) && ch != '(' && ch != ')' && ch != ',' && (ch != '{') && (ch != '}') && (ch != '$') && (this.pos < this.input.length)) {
                                    token.append(this.input.charAt(this.pos));
                                    this.pos++;
                                    ch = this.pos == this.input.length ? '\0' : this.input.charAt(this.pos);
                                    if (ch == org.mwg.core.task.math.CoreMathExpressionEngine.minusSign) {
                                        break;
                                    }
                                }
                                if (!org.mwg.core.task.math.MathEntities.getINSTANCE().operators.keySet().contains(token.toString())) {
                                    throw new Error("Unknown operator '" + token + "' at position " + (this.pos - token.length + 1));
                                }
                            }
                            return this.previousToken = token.toString();
                        };
                        MathExpressionTokenizer.prototype.getPos = function () {
                            return this.pos;
                        };
                        return MathExpressionTokenizer;
                    }());
                    math.MathExpressionTokenizer = MathExpressionTokenizer;
                    var MathFreeToken = (function () {
                        function MathFreeToken(content) {
                            this._content = content;
                        }
                        MathFreeToken.prototype.content = function () {
                            return this._content;
                        };
                        MathFreeToken.prototype.type = function () {
                            return 3;
                        };
                        return MathFreeToken;
                    }());
                    math.MathFreeToken = MathFreeToken;
                    var MathFunction = (function () {
                        function MathFunction(name, numParams) {
                            this.name = name.toUpperCase();
                            this.numParams = numParams;
                        }
                        MathFunction.prototype.getName = function () {
                            return this.name;
                        };
                        MathFunction.prototype.getNumParams = function () {
                            return this.numParams;
                        };
                        MathFunction.prototype.eval = function (p) {
                            if (this.name === "NOT") {
                                return (p[0] == 0) ? 1 : 0;
                            }
                            else if (this.name === "IF") {
                                return !(p[0] == 0) ? p[1] : p[2];
                            }
                            else if (this.name === "RAND") {
                                return Math.random();
                            }
                            else if (this.name === "SIN") {
                                return Math.sin(p[0]);
                            }
                            else if (this.name === "COS") {
                                return Math.cos(p[0]);
                            }
                            else if (this.name === "TAN") {
                                return Math.tan(p[0]);
                            }
                            else if (this.name === "ASIN") {
                                return Math.asin(p[0]);
                            }
                            else if (this.name === "ACOS") {
                                return Math.acos(p[0]);
                            }
                            else if (this.name === "ATAN") {
                                return Math.atan(p[0]);
                            }
                            else if (this.name === "MAX") {
                                return p[0] > p[1] ? p[0] : p[1];
                            }
                            else if (this.name === "MIN") {
                                return p[0] < p[1] ? p[0] : p[1];
                            }
                            else if (this.name === "ABS") {
                                return Math.abs(p[0]);
                            }
                            else if (this.name === "LOG") {
                                return Math.log(p[0]);
                            }
                            else if (this.name === "ROUND") {
                                var factor = Math.pow(10, p[1]);
                                var value = p[0] * factor;
                                var tmp = Math.round(value);
                                return tmp / factor;
                            }
                            else if (this.name === "FLOOR") {
                                return Math.floor(p[0]);
                            }
                            else if (this.name === "CEILING") {
                                return Math.ceil(p[0]);
                            }
                            else if (this.name === "SQRT") {
                                return Math.sqrt(p[0]);
                            }
                            else if (this.name === "SECONDS") {
                                return this.date_to_seconds(p[0]);
                            }
                            else if (this.name === "MINUTES") {
                                return this.date_to_minutes(p[0]);
                            }
                            else if (this.name === "HOURS") {
                                return this.date_to_hours(p[0]);
                            }
                            else if (this.name === "DAY") {
                                return this.date_to_days(p[0]);
                            }
                            else if (this.name === "MONTH") {
                                return this.date_to_months(p[0]);
                            }
                            else if (this.name === "YEAR") {
                                return this.date_to_year(p[0]);
                            }
                            else if (this.name === "DAYOFWEEK") {
                                return this.date_to_dayofweek(p[0]);
                            }
                            return 0;
                        };
                        MathFunction.prototype.date_to_seconds = function (value) {
                            var date = new Date(value);
                            return date.getSeconds();
                        };
                        MathFunction.prototype.date_to_minutes = function (value) {
                            var date = new Date(value);
                            return date.getMinutes();
                        };
                        MathFunction.prototype.date_to_hours = function (value) {
                            var date = new Date(value);
                            return date.getHours();
                        };
                        MathFunction.prototype.date_to_days = function (value) {
                            var date = new Date(value);
                            return date.getDate();
                        };
                        MathFunction.prototype.date_to_months = function (value) {
                            var date = new Date(value);
                            return date.getMonth();
                        };
                        MathFunction.prototype.date_to_year = function (value) {
                            var date = new Date(value);
                            return date.getFullYear();
                        };
                        MathFunction.prototype.date_to_dayofweek = function (value) {
                            var date = new Date(value);
                            return date.getDay();
                        };
                        MathFunction.prototype.type = function () {
                            return 1;
                        };
                        return MathFunction;
                    }());
                    math.MathFunction = MathFunction;
                    var MathOperation = (function () {
                        function MathOperation(oper, precedence, leftAssoc) {
                            this.oper = oper;
                            this.precedence = precedence;
                            this.leftAssoc = leftAssoc;
                        }
                        MathOperation.prototype.getOper = function () {
                            return this.oper;
                        };
                        MathOperation.prototype.getPrecedence = function () {
                            return this.precedence;
                        };
                        MathOperation.prototype.isLeftAssoc = function () {
                            return this.leftAssoc;
                        };
                        MathOperation.prototype.eval = function (v1, v2) {
                            if (this.oper === "+") {
                                return v1 + v2;
                            }
                            else if (this.oper === "-") {
                                return v1 - v2;
                            }
                            else if (this.oper === "*") {
                                return v1 * v2;
                            }
                            else if (this.oper === "/") {
                                return v1 / v2;
                            }
                            else if (this.oper === "%") {
                                return v1 % v2;
                            }
                            else if (this.oper === "^") {
                                return Math.pow(v1, v2);
                            }
                            else if (this.oper === "&&") {
                                var b1 = !(v1 == 0);
                                var b2 = !(v2 == 0);
                                return b1 && b2 ? 1 : 0;
                            }
                            else if (this.oper === "||") {
                                var b1 = !(v1 == 0);
                                var b2 = !(v2 == 0);
                                return b1 || b2 ? 1 : 0;
                            }
                            else if (this.oper === ">") {
                                return v1 > v2 ? 1 : 0;
                            }
                            else if (this.oper === ">=") {
                                return v1 >= v2 ? 1 : 0;
                            }
                            else if (this.oper === "<") {
                                return v1 < v2 ? 1 : 0;
                            }
                            else if (this.oper === "<=") {
                                return v1 <= v2 ? 1 : 0;
                            }
                            else if (this.oper === "==") {
                                return v1 == v2 ? 1 : 0;
                            }
                            else if (this.oper === "!=") {
                                return v1 != v2 ? 1 : 0;
                            }
                            return 0;
                        };
                        MathOperation.prototype.type = function () {
                            return 0;
                        };
                        return MathOperation;
                    }());
                    math.MathOperation = MathOperation;
                })(math = task.math || (task.math = {}));
            })(task = core.task || (core.task = {}));
            var utility;
            (function (utility) {
                var CoreDeferCounter = (function () {
                    function CoreDeferCounter(nb) {
                        this._counter = nb;
                        this._nb_down = new java.util.concurrent.atomic.AtomicInteger(0);
                    }
                    CoreDeferCounter.prototype.count = function () {
                        var previous;
                        var next;
                        do {
                            previous = this._nb_down.get();
                            next = previous + 1;
                        } while (!this._nb_down.compareAndSet(previous, next));
                        if (next == this._counter) {
                            if (this._end != null) {
                                this._end();
                            }
                        }
                    };
                    CoreDeferCounter.prototype.getCount = function () {
                        return this._nb_down.get();
                    };
                    CoreDeferCounter.prototype.then = function (p_callback) {
                        this._end = p_callback;
                        if (this._nb_down.get() == this._counter) {
                            if (p_callback != null) {
                                p_callback();
                            }
                        }
                    };
                    CoreDeferCounter.prototype.wrap = function () {
                        var _this = this;
                        return function (result) {
                            {
                                _this.count();
                            }
                        };
                    };
                    return CoreDeferCounter;
                }());
                utility.CoreDeferCounter = CoreDeferCounter;
                var CoreDeferCounterSync = (function () {
                    function CoreDeferCounterSync(nb) {
                        this._result = null;
                        this._counter = nb;
                        this._nb_down = new java.util.concurrent.atomic.AtomicInteger(0);
                    }
                    CoreDeferCounterSync.prototype.count = function () {
                        this._nb_down.set(this._nb_down.get() + 1);
                        if (this._nb_down.get() == this._counter) {
                            if (this._end != null) {
                                this._end();
                            }
                        }
                    };
                    CoreDeferCounterSync.prototype.getCount = function () {
                        return this._nb_down.get();
                    };
                    CoreDeferCounterSync.prototype.then = function (p_callback) {
                        this._end = p_callback;
                        if (this._nb_down.get() == this._counter) {
                            if (p_callback != null) {
                                p_callback();
                            }
                        }
                    };
                    CoreDeferCounterSync.prototype.wrap = function () {
                        var _this = this;
                        return function (result) {
                            {
                                _this._result = result;
                                _this.count();
                            }
                        };
                    };
                    CoreDeferCounterSync.prototype.waitResult = function () {
                        while (this._nb_down.get() != this._counter) {
                        }
                        return this._result;
                    };
                    return CoreDeferCounterSync;
                }());
                utility.CoreDeferCounterSync = CoreDeferCounterSync;
                var ReadOnlyStorage = (function () {
                    function ReadOnlyStorage(toWrap) {
                        this.wrapped = toWrap;
                    }
                    ReadOnlyStorage.prototype.get = function (keys, callback) {
                        this.wrapped.get(keys, callback);
                    };
                    ReadOnlyStorage.prototype.put = function (stream, callback) {
                        console.error("WARNING: PUT TO A READ ONLY STORAGE");
                    };
                    ReadOnlyStorage.prototype.remove = function (keys, callback) {
                        console.error("WARNING: REMOVE TO A READ ONLY STORAGE");
                    };
                    ReadOnlyStorage.prototype.connect = function (graph, callback) {
                        this.wrapped.connect(graph, callback);
                    };
                    ReadOnlyStorage.prototype.disconnect = function (callback) {
                        this.wrapped.disconnect(callback);
                    };
                    ReadOnlyStorage.prototype.lock = function (callback) {
                        this.wrapped.lock(callback);
                    };
                    ReadOnlyStorage.prototype.unlock = function (previousLock, callback) {
                        this.wrapped.unlock(previousLock, callback);
                    };
                    return ReadOnlyStorage;
                }());
                utility.ReadOnlyStorage = ReadOnlyStorage;
            })(utility = core.utility || (core.utility = {}));
        })(core = mwg.core || (mwg.core = {}));
    })(mwg = org.mwg || (org.mwg = {}));
})(org || (org = {}));
//# sourceMappingURL=mwg.js.map