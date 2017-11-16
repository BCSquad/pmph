package com.bc.pmpheep.back.vo;

import java.io.Serializable;
import org.apache.ibatis.type.Alias;
import com.bc.pmpheep.back.po.Material;
import com.bc.pmpheep.back.po.MaterialExtra;


/**
 * 
 * 教材VO
 *
 * @author Mryang
 *
 * @createDate 2017年11月14日 上午11:25:40
 *
 */
@SuppressWarnings("serial")
@Alias("MaterialVO")
public class MaterialVO implements Serializable{
	//教材
	private Material material;
	//教材通知备注
	private MaterialExtra materialExtra;
	//联系人
	private String   materialContacts;
	//扩展项
	private String   materialExtensions;
	//项目编辑
	private String   materialProjectEditors;
	//通知附件信息         （更新的时候需要）
	private String materialNoticeAttachments;
	//通知备注附件信息 （更新的时候需要）
	private String materialNoteAttachments;
	
	public MaterialVO() {
		super();
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public MaterialExtra getMaterialExtra() {
		return materialExtra;
	}

	public void setMaterialExtra(MaterialExtra materialExtra) {
		this.materialExtra = materialExtra;
	}

	public String getMaterialContacts() {
		return materialContacts;
	}

	public void setMaterialContacts(String materialContacts) {
		this.materialContacts = materialContacts;
	}

	public String getMaterialExtensions() {
		return materialExtensions;
	}

	public void setMaterialExtensions(String materialExtensions) {
		this.materialExtensions = materialExtensions;
	}

	public String getMaterialProjectEditors() {
		return materialProjectEditors;
	}

	public void setMaterialProjectEditors(String materialProjectEditors) {
		this.materialProjectEditors = materialProjectEditors;
	}

	public String getMaterialNoticeAttachments() {
		return materialNoticeAttachments;
	}

	public void setMaterialNoticeAttachments(String materialNoticeAttachments) {
		this.materialNoticeAttachments = materialNoticeAttachments;
	}

	public String getMaterialNoteAttachments() {
		return materialNoteAttachments;
	}

	public void setMaterialNoteAttachments(String materialNoteAttachments) {
		this.materialNoteAttachments = materialNoteAttachments;
	}
	
	
}
