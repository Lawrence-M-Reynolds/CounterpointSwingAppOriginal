package law.raw.composition.components;

import law.raw.composition.components.trackComponents.InstrumentType;

public class InstrumentTrackDetails {
		private int trackNum;
		private String trackName;
		private InstrumentType instrumentType;
		
		public InstrumentTrackDetails(int trackNum, String trackName,
				InstrumentType instrumentType) {
			super();
			this.trackNum = trackNum;
			this.trackName = trackName;
			this.instrumentType = instrumentType;
		}

		public int getTrackNum() {
			return trackNum;
		}

		public String getTrackName() {
			return trackName;
		}

		public InstrumentType getInstrumentType() {
			return instrumentType;
		}

		public void setTrackNum(int trackNum) {
			this.trackNum = trackNum;
		}

		public void setTrackName(String trackName) {
			this.trackName = trackName;
		}

		public void setInstrumentType(InstrumentType instrumentType) {
			this.instrumentType = instrumentType;
		}

	
}
