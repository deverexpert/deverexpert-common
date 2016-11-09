/**
 * Byte 유틸리티
 */
package kr.pe.deverexpert.common.util;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author 이재형
 * 
 */
public class ByteBufferUtil {

	public ByteBuffer m_bb = null;

	public ByteBuffer getByteBuffer(String fileName) {

		FileInputStream fis = null;
		FileChannel fch = null;

		try {
			fis = new FileInputStream(fileName);
			fch = fis.getChannel();
			m_bb = ByteBuffer.allocate((int) fch.size());
			fch.read(m_bb);
			m_bb.flip();
		} catch (Exception e) {
			System.err.println(e);
		} finally {
			try {
				if (fch != null)
					fch.close();
			} catch (Exception e) {
				System.err.println(e);
			}
			try {
				if (fis != null)
					fis.close();
			} catch (Exception e) {
				System.err.println(e);
			}
		}

		return m_bb;
	}

	public String getByteToValue(int size) {
		byte[] b = new byte[size];
		m_bb.get(b);
		return new String(b).trim();
	}

	public String getByteToHex() {
		byte[] b = new byte[1];
		m_bb.get(b);
		StringBuffer sb = new StringBuffer(b.length * 2);
		String hexNumber;
		for (int x = 0; x < b.length; x++) {
			hexNumber = "0" + Integer.toHexString(0xff & b[x]);
			sb.append(hexNumber.substring(hexNumber.length() - 2));
		}
		return sb.toString();
	}

	public int getUnsignedShort() {
		char c = m_bb.getChar();
		return (int) c;
	}

	public Long getUnsignedInt() {
		int n = m_bb.getInt();
		return n & 0xFFFFFFFFL;
	}
}
