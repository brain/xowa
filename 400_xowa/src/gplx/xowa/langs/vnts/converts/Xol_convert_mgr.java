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
package gplx.xowa.langs.vnts.converts; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*; import gplx.xowa.langs.vnts.*;
import gplx.xowa.wikis.nss.*; import gplx.xowa.wikis.data.tbls.*;
public class Xol_convert_mgr {
	private final Ordered_hash tmp_page_list = Ordered_hash_.New_bry();		
	public Xol_convert_regy			Converter_regy() {return converter_regy;} private final Xol_convert_regy converter_regy = new Xol_convert_regy();
	public Xol_convert_wkr[]		Converter_ary() {return wkr_ary;} private Xol_convert_wkr[] wkr_ary;  private int wkr_ary_len;
	public void Init(Xol_vnt_regy regy) {
		int len = regy.Len();
		this.wkr_ary_len = len;
		this.wkr_ary = new Xol_convert_wkr[len];
		for (int i = 0; i < len; i++) {
			Xol_vnt_itm itm = regy.Get_at(i);
			itm.Convert_wkr().Init(converter_regy, itm.Convert_ary());
			wkr_ary[i] = itm.Convert_wkr();
		}
	}
	public byte[] Convert_text(int vnt_idx, byte[] src, int bgn, int end) {
		Bry_bfr tmp_bfr = Xoa_app_.Utl__bfr_mkr().Get_m001();
		Xol_convert_wkr converter = wkr_ary[vnt_idx];
		converter.Convert_text(tmp_bfr, src, bgn, end);
		return tmp_bfr.To_bry_and_rls();
	}
	public Xowd_page_itm Convert_ttl(Xowe_wiki wiki, Xoa_ttl ttl) {return Convert_ttl(wiki, ttl.Ns(), ttl.Page_db());}	// NOTE: not Full_db as ttl.Ns is passed; EX:Шаблон:Šablon:Jez-eng; PAGE:sr.w:ДНК DATE:2014-07-06
	public Xowd_page_itm Convert_ttl(Xowe_wiki wiki, Xow_ns ns, byte[] ttl_bry) {
		Bry_bfr tmp_bfr = Xoa_app_.Utl__bfr_mkr().Get_b512();
		Xowd_page_itm rv = Convert_ttl(wiki, tmp_bfr, ns, ttl_bry);
		tmp_bfr.Mkr_rls();
		return rv;
	}
	private Xowd_page_itm Convert_ttl(Xowe_wiki wiki, Bry_bfr tmp_bfr, Xow_ns ns, byte[] ttl_bry) {	// REF.MW:LanguageConverter.php|findVariantLink
		synchronized (tmp_page_list) {	// THREAD:
			int converted = Convert_ttl__convert_each_vnt(wiki, tmp_bfr, ns, ttl_bry);	// convert ttl for each vnt
			if (converted == 0) return Xowd_page_itm.Null;								// ttl_bry has no conversions; exit;
			// wiki.Data__core_mgr().Tbl__page().Select_in__ns_ttl(Cancelable_.Never, tmp_page_list, wiki.Ns_mgr(), true, 0, converted);	// TODO: use this call; when defaulting test to use db_mgr, not txt_mgr
			wiki.Db_mgr().Load_mgr().Load_by_ttls(Cancelable_.Never, tmp_page_list, Bool_.Y, 0, converted);
			for (int i = 0; i < converted; i++) {
				Xowd_page_itm page = (Xowd_page_itm)tmp_page_list.Get_at(i);
				if (page.Exists()) return page;											// return 1st found page
			}
			return Xowd_page_itm.Null;
		}
	}
	private int Convert_ttl__convert_each_vnt(Xow_wiki wiki, Bry_bfr tmp_bfr, Xow_ns ns, byte[] ttl_bry) {
		tmp_page_list.Clear();
		int rv = 0;
		for (int i = 0; i < wkr_ary_len; i++) {											// convert ttl for each variant
			Xol_convert_wkr converter = wkr_ary[i];
			tmp_bfr.Clear();
			if (!converter.Convert_text(tmp_bfr, ttl_bry)) continue;					// ttl is not converted for variant; ignore
			Xoa_ttl ttl = wiki.Ttl_parse(ns.Id(), tmp_bfr.To_bry_and_clear());			// NOTE: must convert to ttl in order to upper 1st letter; EX:{{jez-eng|sense}} -> Jez-eng; PAGE:sr.w:ДНК DATE:2014-07-06
			if (ttl == null) continue;
			Xowd_page_itm page = new Xowd_page_itm();
			page.Ttl_(ns, ttl.Page_db());
			byte[] converted_ttl = page.Ttl_full_db(); if (tmp_page_list.Has(converted_ttl)) continue;
			tmp_page_list.Add(converted_ttl, page);
			++rv;
		}
		return rv;
	}
}