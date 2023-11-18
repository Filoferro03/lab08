package it.unibo.deathnote.impl;

import it.unibo.deathnote.api.DeathNote;

import java.util.LinkedHashMap;
import java.util.Map;

public final class DeathNoteImpl implements DeathNote {

    private final Map<String,Death> deaths = new LinkedHashMap<>();
    private String lastName;

    @Override
    public String getRule(final int ruleNumber) {
        if (ruleNumber<1 || ruleNumber>RULES.size()) {
            throw new IllegalArgumentException("Rule number " + ruleNumber + "do not exist\n");
        }
        return RULES.get(ruleNumber-1);
    }

    @Override
    public void writeName(final String name) {
        if (name==null) {
            throw new NullPointerException("Name passed as parameter is null");
        }
        deaths.put(name, new Death());
        lastName=name;
    }

    @Override
    public boolean writeDeathCause(final String cause) {
        if (cause == null || deaths.size()==0) {
            throw new IllegalStateException("cause is null or there is no name in the deathnote");
        }
        final Death update = deaths.get(lastName).updateDeathCause(cause);
        return isUpdated(update);
    }

    @Override
    public boolean writeDetails(final String details) {
        if (details == null || deaths.size()==0) {
            throw new IllegalStateException("details are null or there is no name in the deathnote");
        }
        final Death update = deaths.get(lastName).updateDeathDetails(details);
        return isUpdated(update);
    }

    @Override
    public String getDeathCause(final String name) {
        if (isNameWritten(name)==false) {
            throw new IllegalArgumentException("Name is not written in the death note yet");
        }
        return deaths.get(name).cause;
    }

    @Override
    public String getDeathDetails(final String name) {
        if (isNameWritten(name)==false) {
            throw new IllegalArgumentException("Name is not written in the death note yet");
        }
        return deaths.get(name).details;
    }

    @Override
    public boolean isNameWritten(final String name) {
        return deaths.containsKey(name);
    }

    private boolean isUpdated (final Death update) {
        if (update==deaths.get(lastName)) {
            return false;
        }
        else return deaths.replace(lastName, deaths.get(lastName), update);
    }

    private final static class Death {
        private static final String DEFAULT_DEATH = "heart attack";
        private static final long TIME_VALID_CAUSE = 40;
        private static final long TIME_VALID_DETAILS = 6000 + TIME_VALID_CAUSE;
        private final String cause;
        private final String details;
        private final long startTime;

        private Death (final String cause, final String details) {
            this.cause=cause;
            this.details=details;
            this.startTime=System.currentTimeMillis();
        }

        Death () {
            this (DEFAULT_DEATH,"");
        }

        Death updateDeathCause (final String cause) {
            if (System.currentTimeMillis() < startTime + TIME_VALID_CAUSE) {
                return new Death (cause,this.details);
            }
            else return this;
        }
        
        Death updateDeathDetails (final String details) {
            if (System.currentTimeMillis() < startTime + TIME_VALID_DETAILS) {
                return new Death(this.cause, details);
            }
            else return this;
        }
    }
}