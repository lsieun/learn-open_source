/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// --------------------------------------------------------------
//  THIS IS A GENERATED SOURCE FILE. DO NOT EDIT!
//  GENERATED FROM org.apache.flink.api.java.tuple.TupleGenerator.
// --------------------------------------------------------------

package lsieun.flink.api.java.tuple.builder;

import java.util.ArrayList;
import java.util.List;

import lsieun.flink.annotation.Public;
import lsieun.flink.api.java.tuple.Tuple6;


/**
 * A builder class for {@link Tuple6}.
 *
 * @param <T0> The type of field 0
 * @param <T1> The type of field 1
 * @param <T2> The type of field 2
 * @param <T3> The type of field 3
 * @param <T4> The type of field 4
 * @param <T5> The type of field 5
 */
@Public
public class Tuple6Builder<T0, T1, T2, T3, T4, T5> {

	private List<Tuple6<T0, T1, T2, T3, T4, T5>> tuples = new ArrayList<>();

	public Tuple6Builder<T0, T1, T2, T3, T4, T5> add(T0 value0, T1 value1, T2 value2, T3 value3, T4 value4, T5 value5){
		tuples.add(new Tuple6<>(value0, value1, value2, value3, value4, value5));
		return this;
	}

	@SuppressWarnings("unchecked")
	public Tuple6<T0, T1, T2, T3, T4, T5>[] build(){
		return tuples.toArray(new Tuple6[tuples.size()]);
	}
}
