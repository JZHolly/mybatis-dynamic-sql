/**
 *    Copyright 2016 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package examples.simple;

import static org.mybatis.qbe.sql.SqlConditions.isEqualTo;
import static org.mybatis.qbe.sql.insert.InsertSupportBuilder.insertSupport;
import static org.mybatis.qbe.sql.update.UpdateSupportBuilder.updateSupport;

import java.sql.JDBCType;
import java.util.Date;
import java.util.Optional;

import org.mybatis.qbe.mybatis3.MyBatis3Field;
import org.mybatis.qbe.sql.SqlTable;
import org.mybatis.qbe.sql.insert.InsertSupport;
import org.mybatis.qbe.sql.update.UpdateSupport;
import org.mybatis.qbe.sql.update.UpdateSupportBuilder.SetBuilder;

public interface SimpleTableFields {
    SqlTable simpleTable = SqlTable.of("SimpleTable").withAlias("a");
    MyBatis3Field<Integer> id = MyBatis3Field.of("id", JDBCType.INTEGER).inTable(simpleTable);
    MyBatis3Field<String> firstName = MyBatis3Field.of("first_name", JDBCType.VARCHAR).inTable(simpleTable);
    MyBatis3Field<String> lastName = MyBatis3Field.of("last_name", JDBCType.VARCHAR).inTable(simpleTable);
    MyBatis3Field<Date> birthDate = MyBatis3Field.of("birth_date", JDBCType.DATE).inTable(simpleTable);
    MyBatis3Field<String> occupation = MyBatis3Field.of("occupation", JDBCType.VARCHAR).inTable(simpleTable);
    
    static InsertSupport<SimpleTableRecord> buildInsertSupport(SimpleTableRecord record) {
        return insertSupport(record)
                .withFieldMapping(id, "id", SimpleTableRecord::getId)
                .withFieldMapping(firstName, "firstName", SimpleTableRecord::getFirstName)
                .withFieldMapping(lastName, "lastName", SimpleTableRecord::getLastName)
                .withFieldMapping(birthDate, "birthDate", SimpleTableRecord::getBirthDate)
                .withFieldMapping(occupation, "occupation", SimpleTableRecord::getOccupation)
                .buildFullInsert();
    }

    static InsertSupport<SimpleTableRecord> buildInsertSelectiveSupport(SimpleTableRecord record) {
        return insertSupport(record)
                .withFieldMapping(id, "id", SimpleTableRecord::getId)
                .withFieldMapping(firstName, "firstName", SimpleTableRecord::getFirstName)
                .withFieldMapping(lastName, "lastName", SimpleTableRecord::getLastName)
                .withFieldMapping(birthDate, "birthDate", SimpleTableRecord::getBirthDate)
                .withFieldMapping(occupation, "occupation", SimpleTableRecord::getOccupation)
                .buildSelectiveInsert();
    }
    
    static UpdateSupport buildUpdateByPrimaryKeySupport(SimpleTableRecord record) {
        return updateSupport()
                .set(firstName, record.getFirstName())
                .set(lastName, record.getLastName())
                .set(birthDate, record.getBirthDate())
                .set(occupation, record.getOccupation())
                .where(id, isEqualTo(record.getId()))
                .build();
    }

    static UpdateSupport buildUpdateByPrimaryKeySelectiveSupport(SimpleTableRecord record) {
        return updateSupport()
                .set(firstName, Optional.ofNullable(record.getFirstName()))
                .set(lastName, Optional.ofNullable(record.getLastName()))
                .set(birthDate, Optional.ofNullable(record.getBirthDate()))
                .set(occupation, Optional.ofNullable(record.getOccupation()))
                .where(id, isEqualTo(record.getId()))
                .build();
    }

    static SetBuilder updateByExample(SimpleTableRecord record) {
        return updateSupport()
                .set(id, record.getId())
                .set(firstName, record.getFirstName())
                .set(lastName, record.getLastName())
                .set(birthDate, record.getBirthDate())
                .set(occupation, record.getOccupation());
    }

    static SetBuilder updateByExampleSelective(SimpleTableRecord record) {
        return updateSupport()
                .set(id, Optional.ofNullable(record.getId()))
                .set(firstName, Optional.ofNullable(record.getFirstName()))
                .set(lastName, Optional.ofNullable(record.getLastName()))
                .set(birthDate, Optional.ofNullable(record.getBirthDate()))
                .set(occupation, Optional.ofNullable(record.getOccupation()));
    }
}
