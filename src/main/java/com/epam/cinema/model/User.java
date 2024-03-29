package com.epam.cinema.model;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "name", namespace = "http://com.test.soap.dto")
@XmlType(name = "", propOrder = { "id", "firstName", "lastName", "email", "birthday", "messages", "password", "roles"})
public class User extends DomainObject {

    @XmlElement
    private String firstName;

    @XmlElement
    private String lastName;

    @XmlElement
    private String email;

    @XmlElement
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private LocalDate birthday;

    @XmlTransient
    private Set<Ticket> tickets = new TreeSet<>();

    @XmlElement
    private List<String> messages = new ArrayList<>();

    @XmlTransient
    private Role role;

    @XmlElement
    private String password;

    @XmlElement
    private List<Role> roles;

    public User() {
    }

    public User(String firstName, String lastName, String email, LocalDate birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthday = birthday;
    }

    public User(String firstName, String lastName, String email, LocalDate birthday, Set<Ticket> tickets) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthday = birthday;
        this.tickets = tickets;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (birthday != null ? !birthday.equals(user.birthday) : user.birthday != null) return false;
        return tickets != null ? tickets.equals(user.tickets) : user.tickets == null;
    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (tickets != null ? tickets.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%d Name: %-30s Email: %-30s Birthday: %-12s Tickets: %d %-12s Password: %s", getId(), lastName + " " + firstName, email, birthday.toString(), tickets.size(), role, password));
        builder.append("Roles: ");

        if (!isNull(roles) && !roles.isEmpty())
        builder.append(roles.stream().map(Enum::toString).collect(Collectors.joining(",")));


        if (messages.size() > 0) {
            builder.append("\nMessages:\n");
            messages.forEach(s -> builder.append(s + "\n"));
        }

        return builder.toString();
    }
}
