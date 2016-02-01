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
package gplx.xowa.htmls.core.wkrs.imgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.threads.poolables.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.docs.*;
import gplx.xowa.htmls.core.hzips.*; import gplx.xowa.htmls.core.wkrs.imgs.atrs.*;
public class Xoh_img_bare_data implements Xoh_data_itm {
	public int						Tid() {return Xoh_hzip_dict_.Tid__img_bare;}
	public int						Src_bgn() {return src_bgn;} private int src_bgn;
	public int						Src_end() {return src_end;} private int src_end;
	public int						Img_tid() {return img_tid;} private int img_tid;
	public int						Dir_bgn() {return dir_bgn;} private int dir_bgn;
	public int						Dir_end() {return dir_end;} private int dir_end;
	public void Clear() {
		this.src_bgn = src_end = img_tid = dir_bgn = dir_end = -1;
	}
	public boolean Init_by_parse(Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, Gfh_tag_rdr tag_rdr, byte[] src, Gfh_tag img_head, Gfh_tag unused) {
		this.src_bgn = img_head.Src_bgn(); this.src_end = img_head.Src_end();
		Gfh_atr img_src_atr = img_head.Atrs__get_by_or_empty(Gfh_atr_.Bry__src); if (img_src_atr.Val_dat_missing()) return false;
		byte[] root_dir_bry = hctx.Fsys__res();	// NOTE: Fsys_res == Fsys_root on all machines except drd;
		int root_dir_bgn = img_src_atr.Val_bgn();
		int root_dir_end = root_dir_bgn + root_dir_bry.length;
		if (Bry_.Match(src, root_dir_bgn, root_dir_end, root_dir_bry)) {
			int hiero_dir_end = root_dir_end + Url__hiero.length;
			if (Bry_.Match(src, root_dir_end, hiero_dir_end, Url__hiero)) {
				img_tid = Img_tid__hiero;
				dir_bgn = root_dir_bgn;
				dir_end = hiero_dir_end;
                    return true;
			}
		}
		return false;
	}
	public void Init_by_decode__hiero(int src_bgn, int src_end, int dir_bgn, int dir_end) {
		this.img_tid = Img_tid__hiero; this.src_bgn = src_bgn; this.src_end = src_end; this.dir_bgn = dir_bgn; this.dir_end = dir_end;
	}
	public void				Pool__rls	() {pool_mgr.Rls_fast(pool_idx);} private Gfo_poolable_mgr pool_mgr; private int pool_idx;
	public Gfo_poolable_itm	Pool__make	(Gfo_poolable_mgr mgr, int idx, Object[] args) {Xoh_img_bare_data rv = new Xoh_img_bare_data(); rv.pool_mgr = mgr; rv.pool_idx = idx; return rv;}
	public static final byte[] Url__hiero = Bry_.new_a7("bin/any/xowa/xtns/Wikihiero/img/hiero_");
	public static final int Img_tid__hiero = 0;
}
