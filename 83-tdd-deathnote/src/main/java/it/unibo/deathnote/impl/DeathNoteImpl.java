package it.unibo.deathnote.impl;

import it.unibo.deathnote.api.DeathNote;

import java.util.LinkedHashMap;
import java.util.Map;

public final class DeathNoteImpl implements DeathNote {

    private final Map<String,Death> deaths = new LinkedHashMap<>();

    @Override
    public String getRule(int ruleNumber) {
        if (ruleNumber<1 || ruleNumber>RULES.size()) {
            throw new IllegalArgumentException("Rule number " + ruleNumber + "do not exist\n");
        }
        return RULES.get(ruleNumber-1);
    }

    @Override
    public void writeName(String name) {
        if (name==null) {
            throw new NullPointerException("Name passed as parameter is null");
        }
        throw new IllegalArgumentException();
    }

    @Override
    public boolean writeDeathCause(String cause) {
        if (cause == null || deaths.size()==0) {
            throw new IllegalStateException("cause is null or there is no name in the deathnote");
        }
        throw new IllegalArgumentException();
    }

    @Override
    public boolean writeDetails(String details) {
        if (details == null || deaths.size()==0) {
            throw new IllegalStateException("details are null or there is no name in the deathnote");
        }
        throw new IllegalArgumentException();
    }

    @Override
    public String getDeathCause(String name) {
        if (isNameWritten(name)==false) {
            throw new IllegalArgumentException("Name is not written in the death note yet");
        }
        throw new IllegalArgumentException();
    }

    @Override
    public String getDeathDetails(String name) {
        if (isNameWritten(name)==false) {
            throw new IllegalArgumentException("Name is not written in the death note yet");
        }
        throw new IllegalArgumentException();
    }

    @Override
    public boolean isNameWritten(String name) {
        return deaths.containsKey(name);
    }

    private final static class Death {
        private static final String DEFAULT_DEATH = "heart attack";
        private final String cause;
        private final String details;

        public Death (final String cause, final String details) {
            this.cause=cause;
            this.details=details;
        }

        public Death () {
            this.cause=DEFAULT_DEATH;
            this.details="";
        }
        
    }
}