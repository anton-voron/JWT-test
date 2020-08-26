package com.voron.jwtapp.entity;

public enum Status {
	ACTIVE {
		@Override
		public String toString() {
			return "ACTIVE";
		}
	}, 
	INACTIVE {
		@Override
		public String toString() {
			return "INACTIVE";
		}
	}	
}
