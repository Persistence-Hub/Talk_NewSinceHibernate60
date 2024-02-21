package com.thorben.janssen.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.metamodel.spi.EmbeddableInstantiator;
import org.hibernate.metamodel.spi.ValueAccess;

public class MoveInstantiator implements EmbeddableInstantiator {

    Logger log = LogManager.getLogger(this.getClass().getName());

    @Override
    public boolean isInstance(Object object, SessionFactoryImplementor sessionFactory) {
        return object instanceof MoveInstantiator;

    }

    @Override
    public boolean isSameClass(Object object, SessionFactoryImplementor sessionFactory) {
        return object.getClass().equals( MoveInstantiator.class );

    }

    @Override
    public Object instantiate(ValueAccess valueAccess, SessionFactoryImplementor sessionFactory) {
        // valuesAccess contains attribute values in alphabetical order
        final MoveColor color = valueAccess.getValue(0, MoveColor.class);
        final String move = valueAccess.getValue(1, String.class);
        log.info("Instantiate Move embeddable for "+color+" "+move);
        return new Move( color, move );
    }
    
}
