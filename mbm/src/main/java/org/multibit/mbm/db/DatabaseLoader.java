package org.multibit.mbm.db;

import org.multibit.mbm.db.dto.ItemBuilder;
import org.multibit.mbm.db.dao.ItemDao;
import org.multibit.mbm.db.dto.Item;
import org.multibit.mbm.db.dto.ItemField;
import org.multibit.mbm.db.dto.CustomerBuilder;
import org.multibit.mbm.db.dao.CustomerDao;
import org.multibit.mbm.db.dto.Customer;
import org.multibit.mbm.db.dto.RoleBuilder;
import org.multibit.mbm.db.dto.UserBuilder;
import org.multibit.mbm.db.dao.RoleDao;
import org.multibit.mbm.db.dao.UserDao;
import org.multibit.mbm.db.dto.Authority;
import org.multibit.mbm.db.dto.ContactMethod;
import org.multibit.mbm.db.dto.Role;
import org.multibit.mbm.db.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 *  <p>Loads the database to provide default standard data to the application:</p>
 *
 * TODO Move this into the test source tree after the database has been stabilised
 *
 * @since 0.0.1
 *         
 */
@Component
public class DatabaseLoader {

  private static final Logger log = LoggerFactory.getLogger(DatabaseLoader.class);

  @Resource(name = "hibernateItemDao")
  private ItemDao itemDao;

  @Resource(name = "hibernateCustomerDao")
  private CustomerDao customerDao;

  @Resource(name = "hibernateUserDao")
  private UserDao userDao;

  @Resource(name = "hibernateRoleDao")
  private RoleDao roleDao;

  private Role adminRole = null;

  private Role customerRole = null;

  /**
   * Handles the process of initialising the database using the DAOs
   */
  public void initialise() {

    log.info("Populating database");

    buildRolesAndAuthorities();
    buildUsers();
    buildCatalogBooks();

    log.info("Complete");

  }

  private void buildRolesAndAuthorities() {

    // Build the administration Role and Authorities
    adminRole = RoleBuilder.newInstance()
      .withName(Authority.ROLE_ADMIN.name())
      .withDescription("Administration role")
      .withAdminAuthorities()
      .build();
    adminRole = roleDao.saveOrUpdate(adminRole);

    // Build the Customer Role and Authorities
    customerRole = RoleBuilder.newInstance()
      .withName(Authority.ROLE_CUSTOMER.name())
      .withDescription("Customer role")
      .withCustomerAuthorities()
      .build();
    customerRole = roleDao.saveOrUpdate(customerRole);

    roleDao.flush();

  }

  /**
   * Build a demonstration database based on books
   */
  private void buildCatalogBooks() {
    Item book1 = ItemBuilder.newInstance()
      .withSKU("0099410672")
      .withPrimaryFieldDetail(ItemField.TITLE, "Cryptonomicon, by Neal Stephenson", "en")
      .withPrimaryFieldDetail(ItemField.SUMMARY, "'A brilliant patchwork of code-breaking mathematicians and their descendants who are striving to create a data haven in the Philippines...trust me on this one' Guardian", "en")
      .withPrimaryFieldDetail(ItemField.IMAGE_THUMBNAIL_URI, "/mbm/images/catalog/items/2/thumbnail2.png", "en")
      .build();

    itemDao.saveOrUpdate(book1);

    Item book2 = ItemBuilder.newInstance()
      .withSKU("0140296034")
      .withPrimaryFieldDetail(ItemField.TITLE, "A Year In Provence, by Peter Mayle", "en")
      .withPrimaryFieldDetail(ItemField.SUMMARY, "Enjoy an irresistible feast of humour and discover the joys of French rural living with Peter Mayle's bestselling, much-loved account of 'A Year In Provence'.", "en")
      .withPrimaryFieldDetail(ItemField.IMAGE_THUMBNAIL_URI, "/mbm/images/catalog/items/1/thumbnail1.png", "en")
      .build();

    itemDao.saveOrUpdate(book2);

    Item book3 = ItemBuilder.newInstance()
      .withSKU("186126173X")
      .withPrimaryFieldDetail(ItemField.TITLE, "Plumbing and Central Heating, by Mike Lawrence", "en")
      .withPrimaryFieldDetail(ItemField.SUMMARY, "This guide begins with the basic skills of plumbing, which once mastered, can be applied to any situation, from mending a leaking tap to installing a new shower unit.", "en")
      .withPrimaryFieldDetail(ItemField.IMAGE_THUMBNAIL_URI, "/mbm/images/catalog/items/3/thumbnail3.png", "en")
      .build();

    itemDao.saveOrUpdate(book3);

    Item book4 = ItemBuilder.newInstance()
      .withSKU("0575088893")
      .withPrimaryFieldDetail(ItemField.TITLE, "The Quantum Thief, by Hannu Rajaniemi", "en")
      .withPrimaryFieldDetail(ItemField.SUMMARY, "The most exciting SF debut of the last five years - a star to stand alongside Alistair Reynolds and Richard Morgan.", "en")
      .withPrimaryFieldDetail(ItemField.IMAGE_THUMBNAIL_URI, "/mbm/images/catalog/items/4/thumbnail4.png", "en")
      .build();

    itemDao.saveOrUpdate(book4);

    Item book5 = ItemBuilder.newInstance()
      .withSKU("0316184136")
      .withPrimaryFieldDetail(ItemField.TITLE, "The Complete Works of Emily Dickinson, edited by Thomas H Johnson", "en")
      .withPrimaryFieldDetail(ItemField.SUMMARY, "The Complete Poems of Emily Dickinson is the only one-volume edition containing all Emily Dickinson's poems.", "en")
      .withPrimaryFieldDetail(ItemField.IMAGE_THUMBNAIL_URI, "/mbm/images/catalog/items/5/thumbnail5.png", "en")
      .build();

    itemDao.saveOrUpdate(book5);

    itemDao.flush();
  }

  private void buildUsers() {

    // Admin

    User admin = UserBuilder.newInstance()
      .withUUID("trent123")
      .withUsername("trent")
      .withPassword("trent1")
      .withContactMethod(ContactMethod.FIRST_NAME, "Trent")
      .withContactMethod(ContactMethod.EMAIL, "admin@example.org")
      .withRole(adminRole)
      .build();

    userDao.saveOrUpdate(admin);

    // TODO Introduce various staff roles (dispatch, sales etc)

    // Customers
    // Alice
    Customer aliceCustomer = CustomerBuilder.newInstance()
      .build();

    User aliceUser = UserBuilder.newInstance()
      .withUUID("alice123")
      .withUsername("alice")
      .withPassword("alice1")
      .withContactMethod(ContactMethod.FIRST_NAME, "Alice")
      .withContactMethod(ContactMethod.LAST_NAME, "Customer")
      .withContactMethod(ContactMethod.EMAIL, "alice@example.org")
      .withRole(customerRole)
      .withCustomer(aliceCustomer)
      .build();


    userDao.saveOrUpdate(aliceUser);

    // Bob
    Customer bobCustomer = CustomerBuilder.newInstance()
      .build();

    User bob = UserBuilder.newInstance()
      .withUUID("bob123")
      .withUsername("bob")
      .withPassword("bob1")
      .withContactMethod(ContactMethod.FIRST_NAME, "Bob")
      .withContactMethod(ContactMethod.LAST_NAME, "Customer")
      .withContactMethod(ContactMethod.EMAIL, "bob@example.org")
      .withRole(customerRole)
      .build();

    userDao.saveOrUpdate(bob);

    userDao.flush();

  }

  public void setCustomerDao(CustomerDao customerDao) {
    this.customerDao = customerDao;
  }

  public void setItemDao(ItemDao itemDao) {
    this.itemDao = itemDao;
  }
}
