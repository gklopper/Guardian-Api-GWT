package com.gu.api.gwt.server;

import java.io.IOException;
import java.io.InputStream;

public class StringRecordingInputStream extends InputStream {

	private final StringBuilder stringValue = new StringBuilder();
	private final InputStream target;
	
	public StringRecordingInputStream(InputStream target) {
		this.target = target;
	}
	
	public int available() throws IOException {
		return target.available();
	}

	public void close() throws IOException {
		target.close();
	}

	public boolean equals(Object obj) {
		return target.equals(obj);
	}

	public int hashCode() {
		return target.hashCode();
	}

	public void mark(int readlimit) {
		target.mark(readlimit);
	}

	public boolean markSupported() {
		return target.markSupported();
	}

	public int read() throws IOException {
		int value = target.read();
		stringValue.append((char)value);
		return value;
	}

	public int read(byte[] b, int off, int len) throws IOException {
		if (b == null) {
		    throw new NullPointerException();
		} else if ((off < 0) || (off > b.length) || (len < 0) ||
			   ((off + len) > b.length) || ((off + len) < 0)) {
		    throw new IndexOutOfBoundsException();
		} else if (len == 0) {
		    return 0;
		}

		int c = read();
		if (c == -1) {
		    return -1;
		}
		b[off] = (byte)c;

		int i = 1;
		try {
		    for (; i < len ; i++) {
			c = read();
			if (c == -1) {
			    break;
			}
			if (b != null) {
			    b[off + i] = (byte)c;
			}
		    }
		} catch (IOException ee) {
		}
		return i;
	}

	public int read(byte[] b) throws IOException {
		return read(b, 0, b.length);
	}

	public void reset() throws IOException {
		target.reset();
	}

	public long skip(long n) throws IOException {
		return target.skip(n);
	}

	public String toString() {
		if (stringValue.lastIndexOf(">") + 1 < stringValue.length()) {
			return stringValue.substring(0, stringValue.lastIndexOf(">") + 1).toString();
		} else {
			return stringValue.toString();
		}
	}
}
