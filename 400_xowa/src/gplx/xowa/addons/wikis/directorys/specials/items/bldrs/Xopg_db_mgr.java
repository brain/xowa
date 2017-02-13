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
package gplx.xowa.addons.wikis.directorys.specials.items.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.directorys.*; import gplx.xowa.addons.wikis.directorys.specials.*; import gplx.xowa.addons.wikis.directorys.specials.items.*;
import gplx.dbs.*; import gplx.dbs.cfgs.*;
import gplx.xowa.wikis.dbs.*; import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*;	
import gplx.xowa.addons.wikis.directorys.dbs.*;
import gplx.xowa.addons.wikis.directorys.specials.items.bldrs.*;
public class Xopg_db_mgr {
	public static int Create
		( Xowd_page_tbl page_tbl, Xowd_text_tbl text_tbl, Xowd_site_ns_tbl ns_tbl, Db_cfg_tbl cfg_tbl
		, int ns_id, byte[] ttl_page_db, byte[] text_raw, int cat_db_id) {
		// get next page_id
		int page_id = cfg_tbl.Select_int_or("db", "page.id_next", 1);
		cfg_tbl.Upsert_int("db", "page.id_next", page_id + 1);

		// zip if needed
		byte[] text_zip = text_tbl.Zip(text_raw);

		// TODO.XO: should call redirect mgr
		boolean redirect = Bool_.N;

		// do insert
		page_tbl.Insert_bgn();
		text_tbl.Insert_bgn();
		int ns_count = ns_tbl.Select_ns_count(ns_id) + 1;
		try {
			page_tbl.Insert_cmd_by_batch(page_id, ns_id, ttl_page_db, redirect, Datetime_now.Get(), text_raw.length, ns_count, 0, -1, cat_db_id);
			text_tbl.Insert_cmd_by_batch(page_id, text_zip);
			ns_tbl.Update_ns_count(ns_id, ns_count);
		} finally {
			page_tbl.Insert_end();
			text_tbl.Insert_end();
		}
		return page_id;
	}
}
