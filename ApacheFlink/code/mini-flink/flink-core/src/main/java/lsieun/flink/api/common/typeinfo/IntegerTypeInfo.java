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

package lsieun.flink.api.common.typeinfo;

import static lsieun.flink.util.Preconditions.checkArgument;

import java.util.Arrays;
import java.util.HashSet;

import lsieun.flink.annotation.Public;
import lsieun.flink.api.common.typeutils.TypeComparator;
import lsieun.flink.api.common.typeutils.TypeSerializer;

/**
 * Type information for numeric integer primitive types: int, long, byte, short, character.
 */
@Public
public class IntegerTypeInfo<T> extends NumericTypeInfo<T> {

	private static final long serialVersionUID = -8068827354966766955L;

	private static final HashSet<Class<?>> integerTypes = new HashSet<>(
			Arrays.asList(
				Integer.class,
				Long.class,
				Byte.class,
				Short.class,
				Character.class));

	protected IntegerTypeInfo(Class<T> clazz, Class<?>[] possibleCastTargetTypes, TypeSerializer<T> serializer, Class<? extends TypeComparator<T>> comparatorClass) {
		super(clazz, possibleCastTargetTypes, serializer, comparatorClass);

		checkArgument(integerTypes.contains(clazz),
				"The given class %s is not a integer type.", clazz.getSimpleName());
	}
}
