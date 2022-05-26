@TypeDefs({
        @TypeDef(defaultForType = Range.class, typeClass = PostgreSQLRangeType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class),
})
package by.vk.springbootwebnative;

import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
