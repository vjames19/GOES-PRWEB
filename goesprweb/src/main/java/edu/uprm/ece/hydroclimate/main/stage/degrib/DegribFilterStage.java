package edu.uprm.ece.hydroclimate.main.stage.degrib;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.pipeline.StageContext;
import org.apache.commons.pipeline.StageException;
import org.apache.commons.pipeline.validation.ConsumedTypes;
import org.apache.commons.pipeline.validation.ProducedTypes;
import org.apache.log4j.LogMF;
import org.apache.log4j.Logger;

import edu.uprm.ece.hydroclimate.degrib.DegribVariable;
import edu.uprm.ece.hydroclimate.degrib.Degribber;
import edu.uprm.ece.hydroclimate.main.stage.GoesStage;
import edu.uprm.ece.hydroclimate.main.stage.pojo.DegribBundle;
import edu.uprm.ece.hydroclimate.main.stage.pojo.FileBundle;

@ConsumedTypes(FileBundle.class)
@ProducedTypes(DegribBundle.class)
public class DegribFilterStage extends GoesStage {

	private Map<DegribVariable,IOFileFilter> filters;
	private static final Logger logger = Logger.getLogger(DegribFilterStage.class);
	
	@Override
	public void init(StageContext context) {
		// TODO Auto-generated method stub
		super.init(context);
		Degribber degribber = properties.getDegribber();
		List<DegribVariable> variables = degribber.getVariables();
		filters = new HashMap<DegribVariable,IOFileFilter>(variables.size());
		for(DegribVariable var: variables){
			filters.put(var,new WildcardFileFilter(var.getName()));
		}
	}
	
	@Override
	public void process(Object obj) throws StageException {
		FileBundle fb = (FileBundle) obj;
		File file = fb.getData();
		Date date = fb.getDate();
		for(Entry<DegribVariable, IOFileFilter> filter: filters.entrySet()){
			if(filter.getValue().accept(file)){
				LogMF.debug(logger,"Emmitting file {0}", file);
				this.emit(new DegribBundle(file,filter.getKey(),date ));
				return;
			}
		}
	}


}
