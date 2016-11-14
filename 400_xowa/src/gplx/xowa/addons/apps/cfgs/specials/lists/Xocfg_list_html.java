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
import gplx.xowa.specials.*; import gplx.langs.mustaches.*; import gplx.xowa.wikis.pages.*; import gplx.xowa.wikis.pages.tags.*;
import gplx.xowa.addons.apps.cfgs.gui.*;
class Xocfg_list_html extends Xow_special_wtr__base {
	private final    String grp_key, ctx, lang;
	public Xocfg_list_html(String grp_key, String ctx, String lang) {
		this.grp_key = grp_key;
		this.ctx = ctx;
		this.lang = lang;
	}
	@Override protected Io_url Get_addon_dir(Xoa_app app)			{return app.Fsys_mgr().Http_root().GenSubDir_nest("bin", "any", "xowa", "addon", "app", "cfg", "list");}
	@Override protected Io_url Get_mustache_fil(Io_url addon_dir)	{return addon_dir.GenSubFil_nest("bin", "xocfg_list.mustache.html");}
	@Override protected Mustache_doc_itm Bld_mustache_root(Xoa_app app) {
		Xogui_mgr mgr = Xogui_mgr.New(app);
		return mgr.Get_by_grp(grp_key, ctx, lang);
	}
	@Override protected void Bld_tags(Xoa_app app, Io_url addon_dir, Xopage_html_data page_data) {
		Xopg_tag_mgr head_tags = page_data.Head_tags();
		Xopg_tag_wtr_.Add__xocss	(head_tags, app.Fsys_mgr().Http_root());
		Xopg_tag_wtr_.Add__xohelp	(head_tags, app.Fsys_mgr().Http_root());
		Xopg_tag_wtr_.Add__xolog	(head_tags, app.Fsys_mgr().Http_root());
		Xopg_tag_wtr_.Add__xoajax	(head_tags, app.Fsys_mgr().Http_root(), app);

		head_tags.Add(Xopg_tag_itm.New_css_file(addon_dir.GenSubFil_nest("bin", "xocfg_list.css")));
		head_tags.Add(Xopg_tag_itm.New_js_file(addon_dir.GenSubFil_nest("bin", "xocfg_list.js")));
	}
}
