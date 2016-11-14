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
package gplx.xowa.addons.apps.cfgs.specials.lists; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.cfgs.*; import gplx.xowa.addons.apps.cfgs.specials.*;
import gplx.xowa.specials.*; import gplx.core.net.qargs.*;
public class Xocfg_list_special implements Xow_special_page {
	public void Special__gen(Xow_wiki wiki, Xoa_page page, Xoa_url url, Xoa_ttl ttl) {			
		Gfo_qarg_mgr url_args = new Gfo_qarg_mgr().Init(url.Qargs_ary());

		String grp = url_args.Read_str_or("grp", "");
		String ctx = url_args.Read_str_or("ctx", "app");
		String lang = url_args.Read_str_or("lang", wiki.App().User().Wikii().Lang().Key_str());

		new Xocfg_list_html(grp, ctx, lang).Bld_page_by_mustache(wiki.App(), page, this);
	}
	Xocfg_list_special(Xow_special_meta special__meta) {this.special__meta = special__meta;}
	public Xow_special_meta Special__meta()		{return special__meta;} private final    Xow_special_meta special__meta;
	public Xow_special_page Special__clone()	{return this;}
	public static final    Xow_special_page Prototype = new Xocfg_list_special(Xow_special_meta.New_xo("XowaCfgList", "Options"));
}
