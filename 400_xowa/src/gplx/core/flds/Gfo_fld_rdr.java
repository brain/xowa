/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.core.flds; import gplx.*; import gplx.core.*;
public class Gfo_fld_rdr extends Gfo_fld_base {
	private Bry_bfr bfr = Bry_bfr.new_(); private static final byte[] Bry_nil = Bry_.new_a7("\\0");
	public byte[] Data() {return data;} public Gfo_fld_rdr Data_(byte[] v) {data = v; data_len = v.length; pos = 0; return this;} private byte[] data; int data_len;
	public int Pos() {return pos;} public Gfo_fld_rdr Pos_(int v) {pos = v; return this;} private int pos;
	public int Fld_bgn() {return fld_bgn;} public Gfo_fld_rdr Fld_bgn_(int v) {fld_bgn = v; return this;} private int fld_bgn;
	public int Fld_end() {return fld_end;} public Gfo_fld_rdr Fld_end_(int v) {fld_end = v; return this;} private int fld_end;
	public int Fld_idx() {return fld_idx;} private int fld_idx;
	public int Row_idx() {return row_idx;} private int row_idx;
	public void Ini(byte[] data, int pos) {this.data = data; this.data_len = data.length; this.pos = pos;}

	public String Read_str_simple()	{Move_next_simple(); return String_.new_u8(data, fld_bgn, fld_end);}
	public byte[] Read_bry_simple() {Move_next_simple(); return Bry_.Mid(data, fld_bgn, fld_end);}	// was Mid_by_len???; 20120915
	public int Read_int_base85_lenN(int len)	{fld_bgn = pos; fld_end = pos + len - 1	; pos = pos + len + 1	; return Base85_utl.XtoIntByAry(data, fld_bgn, fld_end);}
	public int Read_int_base85_len5()			{fld_bgn = pos; fld_end = pos + 4		; pos = pos + 6			; return Base85_utl.XtoIntByAry(data, fld_bgn, fld_end);}
	public int Read_int() 			{Move_next_simple(); return Bry_.Xto_int_or(data, fld_bgn, fld_end, -1);}
	public byte Read_int_as_byte() 	{Move_next_simple(); return (byte)Bry_.Xto_int_or(data, fld_bgn, fld_end, -1);}
	public byte Read_byte() 		{Move_next_simple(); return data[fld_bgn];}
	public double Read_double() 	{Move_next_simple(); return Bry_.XtoDoubleByPos(data, fld_bgn, fld_end);}
	public DateAdp Read_dte() {// NOTE: fmt = yyyyMMdd HHmmss.fff
		int y = 0, M = 0, d = 0, H = 0, m = 0, s = 0, f = 0;
		if (pos < data_len && data[pos] == row_dlm) {++pos; ++row_idx; fld_idx = 0;} fld_bgn = pos;
		y += (data[fld_bgn +  0] - Byte_ascii.Num_0) * 1000;
		y += (data[fld_bgn +  1] - Byte_ascii.Num_0) *  100;
		y += (data[fld_bgn +  2] - Byte_ascii.Num_0) *   10;
		y += (data[fld_bgn +  3] - Byte_ascii.Num_0);
		M += (data[fld_bgn +  4] - Byte_ascii.Num_0) *   10;
		M += (data[fld_bgn +  5] - Byte_ascii.Num_0);
		d += (data[fld_bgn +  6] - Byte_ascii.Num_0) *   10;
		d += (data[fld_bgn +  7] - Byte_ascii.Num_0);
		H += (data[fld_bgn +  9] - Byte_ascii.Num_0) *   10;
		H += (data[fld_bgn + 10] - Byte_ascii.Num_0);
		m += (data[fld_bgn + 11] - Byte_ascii.Num_0) *   10;
		m += (data[fld_bgn + 12] - Byte_ascii.Num_0);
		s += (data[fld_bgn + 13] - Byte_ascii.Num_0) *   10;
		s += (data[fld_bgn + 14] - Byte_ascii.Num_0);
		f += (data[fld_bgn + 16] - Byte_ascii.Num_0) *  100;
		f += (data[fld_bgn + 17] - Byte_ascii.Num_0) *   10;
		f += (data[fld_bgn + 18] - Byte_ascii.Num_0);
		if (data[fld_bgn + 19] != fld_dlm) throw Exc_.new_("csv date is invalid", "txt", String_.new_u8_by_len(data, fld_bgn, 20));
		fld_end = pos + 20;
		pos = fld_end + 1; ++fld_idx;
		return DateAdp_.new_(y, M, d, H, m, s, f);
	}
	public void Move_next_simple() {
		if (pos < data_len) {
			byte b_cur = data[pos];
			if (b_cur == row_dlm) {
				fld_bgn = fld_end = pos;
				++pos; ++row_idx;
				fld_idx = 0;
				return;
			}
		}
		fld_bgn = pos; 
		if (fld_bgn == data_len) {fld_end = data_len; return;}
		for (int i = fld_bgn; i < data_len; i++) {
			byte b = data[i];
			if 	(b == fld_dlm || b == row_dlm) {
				fld_end = i; pos = i + 1; ++fld_idx;	// position after dlm
				return;
			}
		}
		throw Exc_.new_("fld_dlm failed", "fld_dlm", (char)fld_dlm, "bgn", fld_bgn);
	}
	public String Read_str_escape()	{Move_next_escaped(bfr); return String_.new_u8(bfr.Xto_bry_and_clear());}
	public byte[] Read_bry_escape()	{Move_next_escaped(bfr); return bfr.Xto_bry_and_clear();}
	public void Move_1() {++pos;}
	public void Move_next_escaped() {Move_next_escaped(bfr); bfr.Clear();}
	public int Move_next_simple_fld() {
		Move_next_simple();
		return fld_end;
	}
	public int Move_next_escaped(Bry_bfr trg) {
		//if (pos < data_len && data[pos] == row_dlm) {++pos; ++row_idx; fld_idx = 0;}	// REMOVE:20120919: this will fail for empty fields at end of line; EX: "a|\n"; intent was probably to auto-advance to new row, but this intent should be explicit
		fld_bgn = pos;
		boolean quote_on = false;
		for (int i = fld_bgn; i < data_len; i++) {
			byte b = data[i];
			if 		((b == fld_dlm || b == row_dlm) && !quote_on) {
				fld_end = i; pos = i + 1; ++fld_idx;	// position after dlm
				return pos;
			}
			else if (b == escape_dlm) {	
				++i;
//					if (i == data_len) throw Err_.new_("escape char at end of String");
				b = data[i];
				byte escape_val = decode_regy[b];
				if (escape_val == Byte_ascii.Nil)	{trg.Add_byte(escape_dlm).Add_byte(b);} //throw Err_.new_fmt_("unknown escape key: key={0}", data[i]);
				else								trg.Add_byte(escape_val);
			}
			else if (b == Byte_ascii.Nil) {
				trg.Add(Bry_nil);
			}
			else if (b == quote_dlm) {
				quote_on = !quote_on;
			}
			else
				trg.Add_byte(b);
		}
		return -1;
	}
	public Gfo_fld_rdr Ctor_xdat() {return (Gfo_fld_rdr)super.Ctor_xdat_base();}
	public Gfo_fld_rdr Ctor_sql()  {return (Gfo_fld_rdr)super.Ctor_sql_base();}
	public static Gfo_fld_rdr xowa_()	{return new Gfo_fld_rdr().Ctor_xdat();}
	public static Gfo_fld_rdr sql_()	{return new Gfo_fld_rdr().Ctor_sql();}
}
