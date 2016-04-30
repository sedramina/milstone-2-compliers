import java.lang.System;
import java.io.*;
import java_cup.runtime.Symbol;


class Lexer implements java_cup.runtime.Scanner {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 128;
	private final int YY_EOF = 129;

	//initialize  variables to be used by class
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private int yychar;
	private int yyline;
	private boolean yy_at_bol;
	private int yy_lexical_state;

	Lexer (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	Lexer (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private Lexer () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yychar = 0;
		yyline = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;

//Add code to be executed on initialization of the lexer
	}

	private boolean yy_eof_done = false;
	private final int MATH = 1;
	private final int ITE = 3;
	private final int YYINITIAL = 0;
	private final int EQU = 2;
	private final int yy_state_dtrans[] = {
		0,
		24,
		30,
		127
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		int i;
		for (i = yy_buffer_start; i < yy_buffer_index; ++i) {
			if ('\n' == yy_buffer[i] && !yy_last_was_cr) {
				++yyline;
			}
			if ('\r' == yy_buffer[i]) {
				++yyline;
				yy_last_was_cr=true;
			} else yy_last_was_cr=false;
		}
		yychar = yychar
			+ yy_buffer_index - yy_buffer_start;
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NO_ANCHOR,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NOT_ACCEPT,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NO_ANCHOR,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NO_ANCHOR,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NO_ANCHOR,
		/* 45 */ YY_NOT_ACCEPT,
		/* 46 */ YY_NO_ANCHOR,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NO_ANCHOR,
		/* 49 */ YY_NO_ANCHOR,
		/* 50 */ YY_NO_ANCHOR,
		/* 51 */ YY_NO_ANCHOR,
		/* 52 */ YY_NO_ANCHOR,
		/* 53 */ YY_NO_ANCHOR,
		/* 54 */ YY_NO_ANCHOR,
		/* 55 */ YY_NOT_ACCEPT,
		/* 56 */ YY_NO_ANCHOR,
		/* 57 */ YY_NO_ANCHOR,
		/* 58 */ YY_NO_ANCHOR,
		/* 59 */ YY_NO_ANCHOR,
		/* 60 */ YY_NO_ANCHOR,
		/* 61 */ YY_NOT_ACCEPT,
		/* 62 */ YY_NO_ANCHOR,
		/* 63 */ YY_NOT_ACCEPT,
		/* 64 */ YY_NO_ANCHOR,
		/* 65 */ YY_NOT_ACCEPT,
		/* 66 */ YY_NO_ANCHOR,
		/* 67 */ YY_NOT_ACCEPT,
		/* 68 */ YY_NO_ANCHOR,
		/* 69 */ YY_NOT_ACCEPT,
		/* 70 */ YY_NO_ANCHOR,
		/* 71 */ YY_NOT_ACCEPT,
		/* 72 */ YY_NO_ANCHOR,
		/* 73 */ YY_NOT_ACCEPT,
		/* 74 */ YY_NO_ANCHOR,
		/* 75 */ YY_NOT_ACCEPT,
		/* 76 */ YY_NO_ANCHOR,
		/* 77 */ YY_NOT_ACCEPT,
		/* 78 */ YY_NO_ANCHOR,
		/* 79 */ YY_NOT_ACCEPT,
		/* 80 */ YY_NO_ANCHOR,
		/* 81 */ YY_NOT_ACCEPT,
		/* 82 */ YY_NOT_ACCEPT,
		/* 83 */ YY_NOT_ACCEPT,
		/* 84 */ YY_NOT_ACCEPT,
		/* 85 */ YY_NOT_ACCEPT,
		/* 86 */ YY_NOT_ACCEPT,
		/* 87 */ YY_NOT_ACCEPT,
		/* 88 */ YY_NOT_ACCEPT,
		/* 89 */ YY_NOT_ACCEPT,
		/* 90 */ YY_NOT_ACCEPT,
		/* 91 */ YY_NOT_ACCEPT,
		/* 92 */ YY_NOT_ACCEPT,
		/* 93 */ YY_NOT_ACCEPT,
		/* 94 */ YY_NOT_ACCEPT,
		/* 95 */ YY_NOT_ACCEPT,
		/* 96 */ YY_NOT_ACCEPT,
		/* 97 */ YY_NOT_ACCEPT,
		/* 98 */ YY_NOT_ACCEPT,
		/* 99 */ YY_NOT_ACCEPT,
		/* 100 */ YY_NOT_ACCEPT,
		/* 101 */ YY_NOT_ACCEPT,
		/* 102 */ YY_NOT_ACCEPT,
		/* 103 */ YY_NOT_ACCEPT,
		/* 104 */ YY_NOT_ACCEPT,
		/* 105 */ YY_NOT_ACCEPT,
		/* 106 */ YY_NOT_ACCEPT,
		/* 107 */ YY_NOT_ACCEPT,
		/* 108 */ YY_NOT_ACCEPT,
		/* 109 */ YY_NOT_ACCEPT,
		/* 110 */ YY_NOT_ACCEPT,
		/* 111 */ YY_NOT_ACCEPT,
		/* 112 */ YY_NOT_ACCEPT,
		/* 113 */ YY_NOT_ACCEPT,
		/* 114 */ YY_NOT_ACCEPT,
		/* 115 */ YY_NOT_ACCEPT,
		/* 116 */ YY_NOT_ACCEPT,
		/* 117 */ YY_NOT_ACCEPT,
		/* 118 */ YY_NOT_ACCEPT,
		/* 119 */ YY_NOT_ACCEPT,
		/* 120 */ YY_NOT_ACCEPT,
		/* 121 */ YY_NOT_ACCEPT,
		/* 122 */ YY_NOT_ACCEPT,
		/* 123 */ YY_NOT_ACCEPT,
		/* 124 */ YY_NOT_ACCEPT,
		/* 125 */ YY_NOT_ACCEPT,
		/* 126 */ YY_NOT_ACCEPT,
		/* 127 */ YY_NOT_ACCEPT,
		/* 128 */ YY_NOT_ACCEPT,
		/* 129 */ YY_NOT_ACCEPT,
		/* 130 */ YY_NOT_ACCEPT,
		/* 131 */ YY_NOT_ACCEPT,
		/* 132 */ YY_NOT_ACCEPT,
		/* 133 */ YY_NOT_ACCEPT,
		/* 134 */ YY_NOT_ACCEPT,
		/* 135 */ YY_NOT_ACCEPT,
		/* 136 */ YY_NOT_ACCEPT,
		/* 137 */ YY_NOT_ACCEPT,
		/* 138 */ YY_NOT_ACCEPT,
		/* 139 */ YY_NOT_ACCEPT,
		/* 140 */ YY_NOT_ACCEPT,
		/* 141 */ YY_NOT_ACCEPT,
		/* 142 */ YY_NO_ANCHOR,
		/* 143 */ YY_NOT_ACCEPT,
		/* 144 */ YY_NOT_ACCEPT,
		/* 145 */ YY_NOT_ACCEPT,
		/* 146 */ YY_NOT_ACCEPT,
		/* 147 */ YY_NOT_ACCEPT,
		/* 148 */ YY_NOT_ACCEPT,
		/* 149 */ YY_NOT_ACCEPT,
		/* 150 */ YY_NOT_ACCEPT,
		/* 151 */ YY_NOT_ACCEPT,
		/* 152 */ YY_NO_ANCHOR,
		/* 153 */ YY_NOT_ACCEPT,
		/* 154 */ YY_NO_ANCHOR,
		/* 155 */ YY_NOT_ACCEPT,
		/* 156 */ YY_NO_ANCHOR,
		/* 157 */ YY_NO_ANCHOR,
		/* 158 */ YY_NO_ANCHOR,
		/* 159 */ YY_NO_ANCHOR,
		/* 160 */ YY_NO_ANCHOR,
		/* 161 */ YY_NO_ANCHOR,
		/* 162 */ YY_NO_ANCHOR,
		/* 163 */ YY_NO_ANCHOR,
		/* 164 */ YY_NO_ANCHOR,
		/* 165 */ YY_NO_ANCHOR,
		/* 166 */ YY_NO_ANCHOR,
		/* 167 */ YY_NOT_ACCEPT,
		/* 168 */ YY_NO_ANCHOR,
		/* 169 */ YY_NO_ANCHOR,
		/* 170 */ YY_NO_ANCHOR,
		/* 171 */ YY_NO_ANCHOR,
		/* 172 */ YY_NO_ANCHOR,
		/* 173 */ YY_NO_ANCHOR,
		/* 174 */ YY_NO_ANCHOR,
		/* 175 */ YY_NO_ANCHOR,
		/* 176 */ YY_NO_ANCHOR,
		/* 177 */ YY_NO_ANCHOR,
		/* 178 */ YY_NO_ANCHOR,
		/* 179 */ YY_NO_ANCHOR,
		/* 180 */ YY_NO_ANCHOR,
		/* 181 */ YY_NO_ANCHOR,
		/* 182 */ YY_NO_ANCHOR,
		/* 183 */ YY_NO_ANCHOR,
		/* 184 */ YY_NO_ANCHOR,
		/* 185 */ YY_NO_ANCHOR,
		/* 186 */ YY_NO_ANCHOR,
		/* 187 */ YY_NO_ANCHOR,
		/* 188 */ YY_NO_ANCHOR,
		/* 189 */ YY_NO_ANCHOR,
		/* 190 */ YY_NO_ANCHOR,
		/* 191 */ YY_NO_ANCHOR,
		/* 192 */ YY_NO_ANCHOR,
		/* 193 */ YY_NO_ANCHOR,
		/* 194 */ YY_NO_ANCHOR,
		/* 195 */ YY_NO_ANCHOR,
		/* 196 */ YY_NO_ANCHOR,
		/* 197 */ YY_NO_ANCHOR,
		/* 198 */ YY_NO_ANCHOR,
		/* 199 */ YY_NO_ANCHOR,
		/* 200 */ YY_NO_ANCHOR,
		/* 201 */ YY_NO_ANCHOR,
		/* 202 */ YY_NO_ANCHOR,
		/* 203 */ YY_NO_ANCHOR,
		/* 204 */ YY_NO_ANCHOR,
		/* 205 */ YY_NO_ANCHOR,
		/* 206 */ YY_NO_ANCHOR,
		/* 207 */ YY_NO_ANCHOR,
		/* 208 */ YY_NO_ANCHOR,
		/* 209 */ YY_NO_ANCHOR,
		/* 210 */ YY_NO_ANCHOR,
		/* 211 */ YY_NO_ANCHOR,
		/* 212 */ YY_NO_ANCHOR,
		/* 213 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,130,
"31:10,27,31:2,28,31:18,21,31:3,14,26,31:2,36,37,33:2,31,33,31,33,32:10,31:3" +
",33,31:3,34:26,24,1,25,33,35,31,10,2,18,17,3,30,4,34,5,34,23,19,15,6,12,22," +
"8,34,20,11,9,34:2,29,34,16,7,31,13,31:2,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,214,
"0,1,2,3,1,4,1,3:2,5:5,6,5:2,1:5,7,8,9,1:2,10,11,1,12,13,14,15,16,17,18,1,19" +
",20,1:2,21,22,1,23,24,1,25,1:4,26,21,27,28,29,16:2,30,31,32,33,34,35,36,37," +
"38,39,40,41,42,43,44,45,46,47,48,49,50,6,51,52,53,54,55,56,57,58,59,60,61,6" +
"2,63,64,65,66,67,68,69,70,71,72,73,74,7,8,75,76,16,77,78,17,79,80,81,82,83," +
"84,85,18,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100,101,102,103,104,105," +
"106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124" +
",125,126,127,128,129,130,131,132,133,134,135,136,137,138,139,140,141,142,14" +
"3,144,145,146,147,148,149,150,151,152,153,154,155,156,157,158,159,160,161,1" +
"62,163,164,165,166,167,168,169,170,5,171,172,173,174,175,176")[0];

	private int yy_nxt[][] = unpackFromString(177,38,
"1,2,42:5,3,42:6,4,42:11,5,44,54,42:9,-1:39,6,43,165,207,168,207,-1,207,210," +
"207,179,207:3,211,207,169,207,212,213,-1,207:4,-1:3,207:9,-1,7:12,8,7:13,-1" +
":2,7:9,-1,5:26,-1:2,5:9,-1:2,207:5,-1,207:13,-1,207:4,-1:3,207:9,-1,81:12,1" +
"4,81:13,-1:2,81:9,-1,106:12,22,106:13,-1:2,106:9,-1,107:12,23,107:13,-1:2,1" +
"07:9,1,25,46:5,25,46:5,25,26,46:6,25,46:2,25:2,27,47:2,46:2,25,28,29,46,56," +
"25:2,-1,27:26,-1:2,27:9,-1:32,28,-1:5,1,31,48:5,49,48:5,58:2,48:6,58,48:2,5" +
"8:2,32,50,59,48:2,58,33,34,48,57,58:2,-1:3,108,-1:15,109,-1:19,32:26,-1:2,3" +
"2:9,-1:2,110:5,-1,110:18,-1:2,110:4,33,110:3,150,110,-1:2,110:5,-1,110:18,-" +
"1:2,110:8,150,110,-1:2,113:5,-1,113:18,-1:2,113:9,35,-1,121:12,36,121:13,-1" +
":2,121:9,-1:3,128,-1,129,-1:33,39:26,-1:2,39:9,-1:2,42:5,-1,42:6,-1,42:11,-" +
"1:2,42:10,-1:2,207,181,207:3,-1,207:13,-1,207:4,-1:3,207:9,-1:5,55,-1:11,61" +
",-1:22,46:5,-1,46:5,-1:2,46:6,-1,46:2,-1:5,46:2,-1:3,46,56,-1:4,48:5,-1,48:" +
"5,110:2,48:6,110,48:2,110:2,-1:2,110,48:2,110:3,48,57,150,110,-1:2,207:5,45" +
",207:13,-1,207:4,-1:3,207:9,-1:11,65,-1:28,56:5,-1,56:5,-1:2,56:6,-1,56:2,-" +
"1:5,56:2,-1,56,-1,56:2,-1:4,57:5,-1,57:5,110:2,57:6,110,57:2,110:2,-1:2,110" +
",57:2,110,57,110,57:2,150,110,-1:2,207:5,-1,207:7,9,207:5,-1,207:4,-1:3,207" +
":9,-1:12,67,-1:27,207,10,207:3,-1,207:13,-1,207:4,-1:3,207:9,-1:3,71,-1,73," +
"-1:11,75,-1:22,207,11,207:3,-1,207:13,-1,207:4,-1:3,207:9,-1:3,77,-1:36,207" +
":5,63,207:13,-1,207:4,-1:3,207:9,-1:18,79,-1:21,207:5,-1,207:13,-1,207:4,-1" +
":3,207,12,207:7,-1,81:26,-1:2,81:9,-1:2,207:5,69,207:13,-1,207:4,-1:3,207:9" +
",-1:8,143,-1:31,207:4,13,-1,207:13,-1,207:4,-1:3,207:9,-1:11,82,-1:28,207,1" +
"5,207:3,-1,207:13,-1,207:4,-1:3,207:9,-1:12,83,-1:27,207,16,207:3,-1,207:13" +
",-1,207:4,-1:3,207:9,-1:15,84,-1:23,101,180:5,101,180:13,101,180:4,101,-1:2" +
",180:9,-1:9,85,-1:30,207:5,153,207:13,-1,207:4,-1:3,207:9,-1:3,144,-1:52,87" +
",-1:24,88,-1:47,89,-1:32,90,-1:36,92,-1:44,145,-1:24,93,-1:45,146,-1:31,94," +
"-1:47,95,-1:28,97,-1:47,147,-1:24,99,-1:47,17,-1:35,100,-1:38,148,-1:31,103" +
",-1:44,18,-1:25,101:24,167,101,-1:2,101:9,-1:13,19,-1:35,105,-1:39,20,-1:37" +
",21,-1:30,111,-1:41,112,-1:44,114,-1:22,115,-1:42,151,-1:33,116,-1:53,118,-" +
"1:26,119,-1:36,155,-1:39,120,-1:38,122,-1:38,123,-1:31,124,-1:44,125,-1:31," +
"126,-1:44,37,-1:24,1,38,51:24,39,52:2,51:9,-1:6,130,-1:42,131,-1:43,132,-1:" +
"23,133,-1:41,134,-1:45,40,-1:27,135,-1:43,136,-1:29,137,-1:49,138,-1:27,139" +
",-1:48,140,-1:24,141,-1:47,41,-1:26,207:5,-1,207:9,53,207:3,-1,207:4,-1:3,2" +
"07:9,-1:9,86,-1:43,91,-1:25,96,-1:39,98,-1:35,102,-1:40,104,-1:32,106:26,-1" +
":2,106:9,-1:2,113:5,-1,113:18,-1:2,113:10,-1:3,117,-1:36,207,60,207:3,-1,20" +
"7:13,-1,207:4,-1:3,207:9,-1,107:26,-1:2,107:9,-1:2,207:5,-1,207:3,62,207:9," +
"-1,207:4,-1:3,207:9,-1,121:26,-1:2,121:9,-1:2,207:5,-1,207:11,64,207,-1,207" +
":4,-1:3,207:9,-1:2,207:4,66,-1,207:13,-1,207:4,-1:3,207:9,-1:2,68,207:4,-1," +
"207:13,-1,207:4,-1:3,207:9,-1:2,207:5,-1,207:11,70,207,-1,207:4,-1:3,207:9," +
"-1:2,207:5,-1,207:4,72,207:8,-1,207:4,-1:3,207:9,-1:2,207:5,-1,207:11,74,20" +
"7,-1,207:4,-1:3,207:9,-1:2,207:5,-1,207:11,76,207,-1,207:4,-1:3,207:9,-1:2," +
"207:5,-1,207:13,-1,207:2,78,207,-1:3,207:9,-1:2,207:5,-1,207:12,80,-1,207:4" +
",-1:3,207:9,-1:2,207:4,142,-1,207:13,-1,207:4,-1:3,207:9,-1,101,180:5,149,1" +
"80:13,101,180:3,166,101,-1:2,180:9,-1,101:6,149,101:17,167,101,-1:2,101:9,-" +
"1:2,207:5,-1,207:3,152,207:9,-1,207:4,-1:3,207:9,-1:2,207:5,-1,207:2,154,20" +
"7,185,207:8,-1,207:4,-1:3,207:9,-1:2,207:5,-1,207:3,156,207:9,-1,207:4,-1:3" +
",207:9,-1:2,207:3,157,207,-1,207:13,-1,207:4,-1:3,207:9,-1:2,207:5,-1,207:3" +
",158,207:9,-1,207:4,-1:3,207:9,-1:2,207,159,207:3,-1,207:13,-1,207:4,-1:3,2" +
"07:9,-1:2,207:3,160,207,-1,207:13,-1,207:4,-1:3,207:9,-1:2,207:5,-1,207:3,1" +
"61,207:9,-1,207:4,-1:3,207:9,-1:2,207:5,-1,207:3,162,207:9,-1,207:4,-1:3,20" +
"7:9,-1:2,207,163,207:3,-1,207:13,-1,207:4,-1:3,207:9,-1:2,207:5,-1,207:12,1" +
"64,-1,207:4,-1:3,207:9,-1:2,207,183,207,170,207,-1,207:13,-1,207:4,-1:3,207" +
":9,-1,101,180:5,101,180:13,101,180:3,166,101,-1:2,180:9,-1:2,207:2,171,207:" +
"2,-1,207:13,-1,207:4,-1:3,207:9,-1:2,207,188,207:3,-1,207:13,-1,207:4,-1:3," +
"207:9,-1:2,207:5,-1,207:13,-1,207:4,-1:3,172,207:8,-1:2,207:5,-1,207:13,-1," +
"207,189,207:2,-1:3,207:9,-1:2,207:5,-1,207:10,190,207:2,-1,207:4,-1:3,207:9" +
",-1:2,173,207:4,-1,207:13,-1,207:4,-1:3,207:9,-1:2,192,207:4,-1,207:13,-1,2" +
"07:4,-1:3,207:9,-1:2,207:5,-1,207:13,-1,193,207:3,-1:3,207:9,-1:2,207,209,2" +
"07:3,-1,207:13,-1,207:4,-1:3,207:9,-1:2,207:5,-1,207,194,207:11,-1,207:4,-1" +
":3,207:9,-1:2,207:5,-1,207:3,174,207:9,-1,207:4,-1:3,207:9,-1:2,207:5,-1,20" +
"7:3,195,207:9,-1,207:4,-1:3,207:9,-1:2,207:5,-1,207:2,196,207:10,-1,207:4,-" +
"1:3,207:9,-1:2,207:5,-1,207:7,198,207:5,-1,207:4,-1:3,207:9,-1:2,207:3,175," +
"207,-1,207:13,-1,207:4,-1:3,207:9,-1:2,207:5,-1,207:10,199,207:2,-1,207:4,-" +
"1:3,207:9,-1:2,207:3,176,207,-1,207:13,-1,207:4,-1:3,207:9,-1:2,207,200,207" +
":3,-1,207:13,-1,207:4,-1:3,207:9,-1:2,207:5,-1,207:13,-1,207,201,207:2,-1:3" +
",207:9,-1:2,207:4,202,-1,207:13,-1,207:4,-1:3,207:9,-1:2,207:5,-1,207:2,203" +
",207:10,-1,207:4,-1:3,207:9,-1:2,207:5,-1,207:3,204,207:9,-1,207:4,-1:3,207" +
":9,-1:2,207:2,177,207:2,-1,207:13,-1,207:4,-1:3,207:9,-1:2,207:5,-1,207:10," +
"205,207:2,-1,207:4,-1:3,207:9,-1:2,207:5,-1,207:11,206,207,-1,207:4,-1:3,20" +
"7:9,-1:2,207:5,-1,207:2,178,207:10,-1,207:4,-1:3,207:9,-1:2,207:5,-1,207:10" +
",191,207:2,-1,207:4,-1:3,207:9,-1:2,207:5,-1,207:3,197,207:9,-1,207:4,-1:3," +
"207:9,-1:2,207:5,-1,207:12,182,-1,207:4,-1:3,207:9,-1:2,207:5,-1,207:2,184," +
"207:10,-1,207:4,-1:3,207:9,-1:2,207:5,-1,207:2,186,207:10,-1,207:4,-1:3,207" +
":9,-1:2,207,208,207:3,-1,207,187,207:11,-1,207:4,-1:3,207:9");

	public java_cup.runtime.Symbol next_token ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {

	return new Symbol(sym.EOF, null);
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 0:
						{System.out.println("BODY"+yytext()); return (new Symbol(sym.BODY,yytext()));}
					case -2:
						break;
					case 1:
						
					case -3:
						break;
					case 2:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -4:
						break;
					case 3:
						{
  return new Symbol(sym.ERROR, "Invalid input: " + yytext());
}
					case -5:
						break;
					case 4:
						{System.out.println("MATHMODE"+yytext());yybegin(MATH);return (new Symbol(sym.MATHMODE,yytext()));}
					case -6:
						break;
					case 5:
						{}
					case -7:
						break;
					case 6:
						{System.out.println("NEWLINE"+yytext()); return (new Symbol(sym.NEWLINE,yytext()));}
					case -8:
						break;
					case 8:
						{System.out.println("TEXT"+yytext()); return (new Symbol(sym.TEXT,yytext()));}
					case -9:
						break;
					case 9:
						{System.out.println("ITEM"+yytext()); return (new Symbol(sym.ITEM,yytext()));}
					case -10:
						break;
					case 10:
						{System.out.println("DATE"+yytext()); return (new Symbol(sym.DATE,yytext()));}
					case -11:
						break;
					case 11:
						{System.out.println("TITLE"+yytext()); return (new Symbol(sym.TITLE,yytext()));}
					case -12:
						break;
					case 12:
						{System.out.println("BF"+yytext()); return (new Symbol(sym.BF,yytext()));}
					case -13:
						break;
					case 13:
						{System.out.println("SECTION"+yytext()); return (new Symbol(sym.SECTION,yytext()));}
					case -14:
						break;
					case 14:
						{System.out.println("LABEL"+yytext()); return (new Symbol(sym.LABEL,yytext()));}
					case -15:
						break;
					case 15:
						{System.out.println("SUB"+yytext()); return (new Symbol(sym.SUBTITLE,yytext()));}
					case -16:
						break;
					case 16:
						{System.out.println("MAKE"+yytext()); return (new Symbol(sym.MAKE,yytext()));}
					case -17:
						break;
					case 17:
						{System.out.println("END_ITEM"+yytext()); return (new Symbol(sym.END,yytext()));}
					case -18:
						break;
					case 18:
						{System.out.println("END"+yytext()); return (new Symbol(sym.END,yytext()));}
					case -19:
						break;
					case 19:
						{System.out.println("BEGIN_ITEM"+yytext());yybegin(ITE);return (new Symbol(sym.BEGIN,yytext()));}
					case -20:
						break;
					case 20:
						{System.out.println("BEGIN_EQU"+yytext());yybegin(EQU);return (new Symbol(sym.BEGIN,yytext()));}
					case -21:
						break;
					case 21:
						{System.out.println("BEGIN"+yytext()); return (new Symbol(sym.BEGIN,yytext()));}
					case -22:
						break;
					case 22:
						{ System.out.println("PACKAGE"+yytext()); return (new Symbol(sym.PACKAGE,yytext()));}
					case -23:
						break;
					case 23:
						{System.out.println("start"+yytext()); return (new Symbol(sym.DOCCLASS,yytext()));}
					case -24:
						break;
					case 24:
						{ System.out.println("VARS"+yytext()); return( new Symbol(sym.VAR,yytext()));}
					case -25:
						break;
					case 25:
						{
  return new Symbol(sym.ERROR, "Invalid input: " + yytext());
}
					case -26:
						break;
					case 26:
						{System.out.println("MATHMODE"+yytext()); yybegin(YYINITIAL);}
					case -27:
						break;
					case 27:
						{}
					case -28:
						break;
					case 28:
						{System.out.println("DIGITS"+yytext()); return (new Symbol(sym.NM,yytext()));}
					case -29:
						break;
					case 29:
						{System.out.println("OPERATORS"+yytext()); return (new Symbol(sym.OPERATOR,yytext()));}
					case -30:
						break;
					case 30:
						{System.out.println("VARS"+yytext()); return (new Symbol(sym.VAR,yytext()));}
					case -31:
						break;
					case 31:
						{
  return new Symbol(sym.ERROR, "Invalid input: " + yytext());
}
					case -32:
						break;
					case 32:
						{}
					case -33:
						break;
					case 33:
						{System.out.println("DIGITS"+yytext()); return (new Symbol(sym.NM,yytext()));}
					case -34:
						break;
					case 34:
						{System.out.println("OPERATORS"+yytext()); return (new Symbol(sym.OPERATOR,yytext()));}
					case -35:
						break;
					case 35:
						{System.out.println("FUN"+yytext()); return (new Symbol(sym.FUNC,yytext()));}
					case -36:
						break;
					case 36:
						{System.out.println("LABEL"+yytext()); return (new Symbol(sym.LABEL,yytext()));}
					case -37:
						break;
					case 37:
						{System.out.println("END_EQU"+yytext()); yybegin(YYINITIAL);return (new Symbol(sym.END,yytext()));}
					case -38:
						break;
					case 38:
						{
  return new Symbol(sym.ERROR, "Invalid input: " + yytext());
}
					case -39:
						break;
					case 39:
						{}
					case -40:
						break;
					case 40:
						{System.out.println("ITEM"+yytext()); yybegin(YYINITIAL);return (new Symbol(sym.ITEM,yytext()));}
					case -41:
						break;
					case 41:
						{System.out.println("END_ITEM"+yytext()); yybegin(YYINITIAL);return (new Symbol(sym.ERROR,"Line: "+yyline+" Item list with missing \\item"));}
					case -42:
						break;
					case 42:
						{System.out.println("BODY"+yytext()); return (new Symbol(sym.BODY,yytext()));}
					case -43:
						break;
					case 43:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -44:
						break;
					case 44:
						{}
					case -45:
						break;
					case 46:
						{System.out.println("VARS"+yytext()); return (new Symbol(sym.VAR,yytext()));}
					case -46:
						break;
					case 47:
						{}
					case -47:
						break;
					case 48:
						{System.out.println("VARS"+yytext()); return (new Symbol(sym.VAR,yytext()));}
					case -48:
						break;
					case 49:
						{
  return new Symbol(sym.ERROR, "Invalid input: " + yytext());
}
					case -49:
						break;
					case 50:
						{}
					case -50:
						break;
					case 51:
						{
  return new Symbol(sym.ERROR, "Invalid input: " + yytext());
}
					case -51:
						break;
					case 52:
						{}
					case -52:
						break;
					case 53:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -53:
						break;
					case 54:
						{}
					case -54:
						break;
					case 56:
						{System.out.println("VARS"+yytext()); return (new Symbol(sym.VAR,yytext()));}
					case -55:
						break;
					case 57:
						{System.out.println("VARS"+yytext()); return (new Symbol(sym.VAR,yytext()));}
					case -56:
						break;
					case 58:
						{
  return new Symbol(sym.ERROR, "Invalid input: " + yytext());
}
					case -57:
						break;
					case 59:
						{}
					case -58:
						break;
					case 60:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -59:
						break;
					case 62:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -60:
						break;
					case 64:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -61:
						break;
					case 66:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -62:
						break;
					case 68:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -63:
						break;
					case 70:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -64:
						break;
					case 72:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -65:
						break;
					case 74:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -66:
						break;
					case 76:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -67:
						break;
					case 78:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -68:
						break;
					case 80:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -69:
						break;
					case 142:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -70:
						break;
					case 152:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -71:
						break;
					case 154:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -72:
						break;
					case 156:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -73:
						break;
					case 157:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -74:
						break;
					case 158:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -75:
						break;
					case 159:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -76:
						break;
					case 160:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -77:
						break;
					case 161:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -78:
						break;
					case 162:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -79:
						break;
					case 163:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -80:
						break;
					case 164:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -81:
						break;
					case 165:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -82:
						break;
					case 166:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -83:
						break;
					case 168:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -84:
						break;
					case 169:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -85:
						break;
					case 170:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -86:
						break;
					case 171:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -87:
						break;
					case 172:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -88:
						break;
					case 173:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -89:
						break;
					case 174:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -90:
						break;
					case 175:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -91:
						break;
					case 176:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -92:
						break;
					case 177:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -93:
						break;
					case 178:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -94:
						break;
					case 179:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -95:
						break;
					case 180:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -96:
						break;
					case 181:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -97:
						break;
					case 182:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -98:
						break;
					case 183:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -99:
						break;
					case 184:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -100:
						break;
					case 185:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -101:
						break;
					case 186:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -102:
						break;
					case 187:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -103:
						break;
					case 188:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -104:
						break;
					case 189:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -105:
						break;
					case 190:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -106:
						break;
					case 191:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -107:
						break;
					case 192:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -108:
						break;
					case 193:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -109:
						break;
					case 194:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -110:
						break;
					case 195:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -111:
						break;
					case 196:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -112:
						break;
					case 197:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -113:
						break;
					case 198:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -114:
						break;
					case 199:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -115:
						break;
					case 200:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -116:
						break;
					case 201:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -117:
						break;
					case 202:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -118:
						break;
					case 203:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -119:
						break;
					case 204:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -120:
						break;
					case 205:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -121:
						break;
					case 206:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -122:
						break;
					case 207:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -123:
						break;
					case 208:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -124:
						break;
					case 209:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -125:
						break;
					case 210:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -126:
						break;
					case 211:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -127:
						break;
					case 212:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -128:
						break;
					case 213:
						{System.out.println("ERR"+yytext()); return (new Symbol(sym.ERROR," Line: "+yyline+" Undefined control sequence: "+ yytext()));}
					case -129:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}
