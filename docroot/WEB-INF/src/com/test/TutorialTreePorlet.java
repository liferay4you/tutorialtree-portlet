package com.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.apache.log4j.Category;
import org.eclipse.jdt.internal.compiler.ast.ForeachStatement;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class TutorialTreePorlet
 */
public class TutorialTreePorlet extends MVCPortlet {
 
	@Override
	public void doView(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {
		// TODO Auto-generated method stub
		try {
		AssetVocabulary assetVocabularytemp = null;
		
		//creamos una variable del tipo array para guardar todas las categorías.
		List<AssetCategory> assetTemp = new ArrayList<AssetCategory>();
		
		
		StringBuffer sb = new StringBuffer();
		
	
			//Creamos una lista con todos los vocabularios de nuestra categoría.
			List<AssetVocabulary> vocabularyList = AssetVocabularyLocalServiceUtil.getAssetVocabularies
														(0, AssetVocabularyLocalServiceUtil.getAssetVocabulariesCount());
			
			
			//Creamos una variable llamada "AssetVocabularytemp" que contiene nuestro vocabulario "tutoriales".
			for (AssetVocabulary assetVocabulary : vocabularyList) {
				
				if (assetVocabulary.getName().equalsIgnoreCase("tutoriales")) {
					
					assetVocabularytemp = assetVocabulary;

				}
				
			}
			
			
			
			
			//Creamos una lista con todas las categorías de nuestro vocabulario.
			List<AssetCategory> categoryList = AssetCategoryLocalServiceUtil.getVocabularyCategories
						(assetVocabularytemp.getVocabularyId(), 0, AssetCategoryLocalServiceUtil.getAssetCategoriesCount(), null);
			

			//Añadimos todas las categorías padre.
			for (AssetCategory assetCategory : categoryList) {
				
					assetTemp.add(assetCategory);
				
			}
		
		
			sb.append("<ul>");
			
			//De nuestra lista de categorias seleccionamos las categorias padre y llamamos al método "fillHtml" para rellenar nuestro html.
			for (AssetCategory assetCategory : categoryList) {
				
				if (assetCategory.getParentCategoryId() == 0){
					
					fillHtml(assetCategory,sb);
				
				}
				
			}
			
			sb.append("</ul>");
			
			System.out.println(sb);
		
		
		
		renderRequest.setAttribute("categorias", sb.toString());
		super.doView(renderRequest, renderResponse);
		
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	 public StringBuffer fillHtml(AssetCategory category,StringBuffer sb){
	
	 try {
		sb.append("<li>"); sb.append(category.getName()); sb.append("</li>");
		
		List<AssetCategory> offspring = AssetCategoryLocalServiceUtil.getChildCategories(category.getCategoryId());
		
		if (offspring != null) {
			
			for (AssetCategory assetCategory : offspring) {
				
				sb.append("<ul>");
					fillHtml(assetCategory,sb);
				sb.append("</ul>");
					
			}
				
		} 
		
		
	 } catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	 return sb;
	 
	 }
			
}


	 


