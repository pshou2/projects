package learn.tier.data;

import learn.tier.data.mappers.CategoryMapper;
import learn.tier.models.Category;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class CategoryJdbcTemplateRepository implements CategoryRepository{
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Category> rowMapper = new CategoryMapper();

    public CategoryJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Category> findAll() {
        final String sql = """
        select
            category_id,
            `name`
        from category;
        """;

        return jdbcTemplate.query(sql, rowMapper);
    }



    @Override
    public Category findById(int categoryId){
        final String sql = """
        select
        category_id, 
        `name`
        from category 
        where category_id = ?;
        """;

        return jdbcTemplate.query(sql, rowMapper, categoryId).stream().findFirst().orElse(null);
        //return jdbcTemplate.queryForObject(sql, rowMapper, categoryId);
    }
    @Override
    public Category add(Category category){
        final String sql = "insert into category (`name`) values (?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, category.getName());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        category.setCategoryId(keyHolder.getKey().intValue());
        return category;
    }
   @Override
   public boolean update(Category category){
       final String sql = "update category set " +
               "`name` = ? " +
               "where category_id = ?;";

      return jdbcTemplate.update(sql,
               category.getName(),
               category.getCategoryId()) > 0;


    }
    @Override
    public boolean deleteById(int categoryId){
        return jdbcTemplate.update("delete from category where category_id = ?;", categoryId) > 0;
    }
}
