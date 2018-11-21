<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE sqlMap PUBLIC "-//ibatis.apache.org//DTD SQL Map 2.0//EN"
		"http://ibatis.apache.org/dtd/sql-map-2.dtd">

<sqlMap namespace="${modelJava}">

    <!-- 别名 -->
	<typeAlias alias="${modelValue}" type="com.${packageName}.model.${modelJava}"/>

    <typeAlias alias="${modelValue}Vo" type="com.api.${packageName}.vo.${modelJava}Vo"/>

    <typeAlias alias="${modelValue}VoReq" type="com.api.${packageName}.vo.${modelJava}VoReq"/>


    <!-- 用于select查询公用抽取的列 -->
	<sql id="modelColumns">
    ${modelColumns}
    </sql>

    <!-- 动态查询条件 -->
	<sql id="dynamicWhere">
		<dynamic prepend="WHERE">
          <#list dynamicWhereList as var>
              ${var}
           </#list>
		</dynamic>
	</sql>


    <!-- 	说明:通过id获取实体信息
        匹配条件: @param id 实体的id
        返回字段： 全部（如果有特殊构造的字段要添加字段说明） -->
    <select id="getById" resultClass="${modelValue}" parameterClass="java.lang.${keyType}">
        SELECT
        <include refid="modelColumns"/>
        FROM ${table} a
        WHERE a.${keyColumn} = #${keyVar}#
    </select>


	<!--查询-->
	<select id="select"  resultClass="${modelValue}" parameterClass="java.util.Map">
		SELECT <include refid="modelColumns"/> FROM ${table} a
		<include refid="dynamicWhere"/>
	</select>

    <!-- 说明:根据条件分页查询 -->
    <select id="pageSelect" resultClass="${modelValue}Vo" parameterClass="java.util.Map">
        SELECT <include refid="modelColumns"/> FROM ${table} a
        <include refid="dynamicWhere"/>
    </select>

    <!-- 查询记录数 根据特定条件 -->
	<select id="count" resultClass="long" parameterClass="java.util.Map">
		select count(1) from ${table} a
		<include refid="dynamicWhere"/>
	</select>

    <insert id="insert" parameterClass="${modelJava}">
        insert into ${table}(${columnStr})
        VALUES(${javaStr})
    </insert>

     <update id="update" parameterClass="${modelJava}">
       update ${table} a
		<dynamic prepend="set">
             <#list dynamicUpdateList as var>
              ${var}
           </#list>
		</dynamic>
		where a.${keyColumn} = #${keyVar}#
    </update>

    <insert id="batchSave" parameterClass="java.util.List">
        insert into ${table} (${columnStr})
        values

         <iterate conjunction=",">
            <![CDATA[
                (${batchjavaStr})
        ]]>
        </iterate>
        <!--下面这句必须加，不然会提示找不到SELECT select * from dual
        <![CDATA[
            SELECT * FROM dual
          ]]>-->
    </insert>

</sqlMap>


