/*-
* Copyright (c) 2017 (Graeme Jenkinson)
* All rights reserved.
*
* This software was developed by BAE Systems, the University of Cambridge
* Computer Laboratory, and Memorial University under DARPA/AFRL contract
* FA8650-15-C-7558 ("CADETS"), as part of the DARPA Transparent Computing
* (TC) research program.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions
* are met:
* 1. Redistributions of source code must retain the above copyright
* notice, this list of conditions and the following disclaimer.
* 2. Redistributions in binary form must reproduce the above copyright
* notice, this list of conditions and the following disclaimer in the
* documentation and/or other materials provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY THE AUTHOR AND CONTRIBUTORS ``AS IS'' AND
* ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
* IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
* ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR CONTRIBUTORS BE LIABLE
* FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
* DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
* OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
* HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
* LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
* OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
* SUCH DAMAGE.
*
*/

package uk.ac.cam.cl.cadets.kafka.streams.reconciliation;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerRecord;

/***
 * Extract the timestamp from a CADETS Json record.
 * 
 * Each record contains a timestamp field: {"time": ..., ...}. The value
 * of the time field is the walltimestamp in ns. 
 */
final public class TimestampExtractor
    implements org.apache.kafka.streams.processor.TimestampExtractor {

    public final static String TIME_FIELD = "time";

    @Override
    public long extract(final ConsumerRecord<Object, Object> consumerRecord,
        final long previousTimestamp) {

        final Object val = consumerRecord.value();
        if (val == null)
	    return 0;
        else
	    return TimeUnit.NANOSECONDS.toMillis(
                ((JsonNode) val).get(TIME_FIELD).asLong());
    }
}
