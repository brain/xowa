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
package gplx.xowa.addons.apps.updates.specials; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.updates.*;
import gplx.langs.mustaches.*;
class Xoa_update_itm implements Mustache_doc_itm {
	private final    String version, date, priority, summary, details;
	public Xoa_update_itm(String version, String date, String priority, String summary, String details) {
		this.version = version;
		this.date = date;
		this.priority = priority;
		this.summary = summary;
		this.details = details;
	}
	public boolean Mustache__write(String k, Mustache_bfr bfr) {
		if		(String_.Eq(k, "version"))			bfr.Add_str_u8(version);
		else if	(String_.Eq(k, "date"))				bfr.Add_str_u8(date);
		else if	(String_.Eq(k, "priority"))			bfr.Add_str_u8(priority);
		else if	(String_.Eq(k, "summary"))			bfr.Add_str_u8(summary);
		else if	(String_.Eq(k, "details"))			bfr.Add_str_u8(details);
		return true;
	}
	public Mustache_doc_itm[] Mustache__subs(String key) {
		return Mustache_doc_itm_.Ary__empty;
	}
	public static Mustache_doc_itm Load(Io_url app_root) {
		// get from internet
//			Io_url trg_url = app_root.GenSubFil_nest("app", "update", "xowa_update_info.sqlite3");
//			String src_url = "http://xowa.org/admin/app_update/xowa_update_info.sqlite3";
//			Io_mgr.Instance.DownloadFil(src_url, trg_url);

		return new Xoa_update_itm("", "", "", "", "");
	}
}
