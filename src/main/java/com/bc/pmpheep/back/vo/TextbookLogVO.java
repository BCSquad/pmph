package com.bc.pmpheep.back.vo;


import org.apache.ibatis.type.Alias;

/**
 *@author MrYang 
 *@CreateDate 2017年11月28日 上午11:49:49
 *
 **/
@SuppressWarnings("serial")
@Alias("TextbookLogVO")
public class TextbookLogVO implements java.io.Serializable {
		/**
		 * 主键
		 */
		private Long id;
		/**
		 * 变更内容
		 */
		private String detail;

		public TextbookLogVO() {
			super();
		}
		
		public TextbookLogVO(Long id,String detail) {
			super();
			this.id=id;
			this.detail =detail;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getDetail() {
			return detail;
		}

		public void setDetail(String detail) {
			this.detail = detail;
		}

		@Override
		public String toString() {
			return "{id:" + id + ", detail:\"" + detail + "\"}";
		}
		
	}
