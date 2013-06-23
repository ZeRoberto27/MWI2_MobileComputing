package at.technikum.wien.fh.wi.ma.shitdroid.service;

import java.util.Collection;

import at.technikum.wien.fh.wi.ma.shitdroid.entity.WcEntity;

public interface IWcService {
	
	Collection<WcEntity> getWCs();
	
	void saveWcs(Collection<WcEntity> wcs);
	
	WcEntity getWcById(String id);
	
}
