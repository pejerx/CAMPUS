package edu.cit.garol.campus;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link CampusApplication}.
 */
@Generated
public class CampusApplication__BeanDefinitions {
  /**
   * Get the bean definition for 'campusApplication'.
   */
  public static BeanDefinition getCampusApplicationBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(CampusApplication.class);
    beanDefinition.setInstanceSupplier(CampusApplication::new);
    return beanDefinition;
  }
}
