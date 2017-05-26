#!/usr/bin/env bash
#
# Copyright 2017 The GreyCat Authors.  All rights reserved.
# <p>
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
# <p>
# http://www.apache.org/licenses/LICENSE-2.0
# <p>
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

export JAVA_HOME="$(/usr/libexec/java_home -v 1.8)"
gcc -O2 -DNDEBUG -I $JAVA_HOME/include -I $JAVA_HOME/include/darwin -shared org_mwg_NativeHasherHelper.c -o ../resources/natives.dylib
