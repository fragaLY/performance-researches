@TypeDefs({
        @TypeDef(defaultForType = Range.class, typeClass = PostgreSQLRangeType.class),
})
package by.vk.datagen;

import com.vladmihalcea.hibernate.type.range.PostgreSQLRangeType;
import com.vladmihalcea.hibernate.type.range.Range;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
