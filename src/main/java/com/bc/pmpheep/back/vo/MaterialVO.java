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
	//教材主任id
	private long director;
	//教材主任
	private String directorName; 
	//教材类型字符串 [1,2,3,4]
	private String materialType;
	//教材通知备注
	private MaterialExtra materialExtra;
	//联系人
	private String   materialContacts;
	//扩展项
	private String   materialExtensions;
	//项目编辑
	private String   materialProjectEditors;
	//通知附件信息       
	private String materialNoticeAttachments;
	//通知备注附件信息 
	private String materialNoteAttachments;
	//策划编辑权限  项目编辑权限8位二进制字符串
	private String cehuaPowers;
	//项目编辑权限  项目编辑权限8位二进制字符串
	private String projectEditorPowers;
	private String materialCheckPositions;

	public MaterialVO() {
		super();
	}


    public String getMaterialCheckPositions() {
        return materialCheckPositions;
    }

    public void setMaterialCheckPositions(String materialCheckPositions) {
        this.materialCheckPositions = materialCheckPositions;
    }

    public MaterialVO(
			Material material, 
			String directorName,
			String materialType,
			MaterialExtra materialExtra,
			String materialContacts, 
			String materialExtensions,
			String materialProjectEditors, 
			String materialNoticeAttachments,
			String materialNoteAttachments,
			String cehuaPowers,
			String projectEditorPowers) {
		super();
		this.material = material;
		this.directorName = directorName;
		this.materialType = materialType;
		this.materialExtra = materialExtra;
		this.materialContacts = materialContacts;
		this.materialExtensions = materialExtensions;
		this.materialProjectEditors = materialProjectEditors;
		this.materialNoticeAttachments = materialNoticeAttachments;
		this.materialNoteAttachments = materialNoteAttachments;
		this.cehuaPowers = cehuaPowers;
		this.projectEditorPowers = projectEditorPowers;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}
	
	public String getDirectorName() {
		return directorName;
	}

	public void setDirectorName(String directorName) {
		this.directorName = directorName;
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
	
	public String getMaterialType() {
		return materialType;
	}

	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}

	public String getCehuaPowers() {
		return cehuaPowers;
	}

	public void setCehuaPowers(String cehuaPowers) {
		this.cehuaPowers = cehuaPowers;
	}

	public String getProjectEditorPowers() {
		return projectEditorPowers;
	}

	public void setProjectEditorPowers(String projectEditorPowers) {
		this.projectEditorPowers = projectEditorPowers;
	}

	@Override
	public String toString() {
		return "{material:" + material + ", directorName:" + directorName
				+ ", materialType:" + materialType + ", materialExtra:"
				+ materialExtra + ", materialContacts:" + materialContacts
				+ ", materialExtensions:" + materialExtensions
				+ ", materialProjectEditors:" + materialProjectEditors
				+ ", materialNoticeAttachments:" + materialNoticeAttachments
				+ ", materialNoteAttachments:" + materialNoteAttachments
				+ ", cehuaPowers:" + cehuaPowers + ", projectEditorPowers:"
				+ projectEditorPowers + "}";
	}


	public long getDirector() {
		return director;
	}

	public void setDirector(long director) {
		this.director = director;
	}
}
