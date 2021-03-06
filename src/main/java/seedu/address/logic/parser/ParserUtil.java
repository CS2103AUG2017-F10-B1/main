package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.relationship.ConfidenceEstimate;
import seedu.address.model.relationship.RelationshipDirection;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 * {@code ParserUtil} contains methods that take in {@code Optional} as parameters. However, it goes against Java's
 * convention (see https://stackoverflow.com/a/39005452) as {@code Optional} should only be used a return type.
 * Justification: The methods in concern receive {@code Optional} return values from other methods as parameters and
 * return {@code Optional} values based on whether the parameters were present. Therefore, it is redundant to unwrap the
 * initial {@code Optional} before passing to {@code ParserUtil} as a parameter and then re-wrap it into an
 * {@code Optional} return value inside {@code ParserUtil} methods.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INSUFFICIENT_PARTS = "Number of parts must be more than 1.";
    public static final String MESSAGE_INVALID_DIRECTION = "Direction must be directed or undirected.";
    public static final String MESSAGE_INVALID_PREFIX = "Prefix is not of the required format. "
            + "Required formats are \"n/\" or \"p/\" or \"e/\" or \"a/\" or \"r/\"."
            + "\n"
            + "Example: sort n/";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws IllegalValueException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws IllegalValueException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new IllegalValueException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }
    //@@author TanYikai
    /**
     * Parses the String to return int according to the corresponding prefix
     * 0,1,2,3,4 corresponds to PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_REMARK respectively
     * @throws IllegalValueException if the specified prefix is invalid (not n/, p/, e/, a/ or r/).
     */
    public static Option parseSortOption(String prefix) throws IllegalValueException {
        String trimmedPrefix = prefix.trim();
        Option sortOption;

        if (trimmedPrefix.equals(PREFIX_NAME.toString())) {
            sortOption = Option.NAME;
        } else if (trimmedPrefix.equals(PREFIX_PHONE.toString())) {
            sortOption = Option.PHONE;
        } else if (trimmedPrefix.equals(PREFIX_EMAIL.toString())) {
            sortOption = Option.EMAIL;
        } else if (trimmedPrefix.equals(PREFIX_ADDRESS.toString())) {
            sortOption = Option.ADDRESS;
        } else if (trimmedPrefix.equals(PREFIX_REMARK.toString())) {
            sortOption = Option.REMARK;
        } else {
            throw new IllegalValueException(MESSAGE_INVALID_PREFIX);
        }
        return sortOption;
    }

    /**
     * The enum for the various sort options available.
     * NAME, PHONE, EMAIL, ADDRESS, REMARK means sort by name, phone, eail, address or remark respectively
     */
    public enum Option {
        NAME, PHONE, EMAIL, ADDRESS, REMARK
    }
    //@@author
    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<Name>} if {@code name} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Name> parseName(Optional<String> name) throws IllegalValueException {
        requireNonNull(name);
        return name.isPresent() ? Optional.of(new Name(name.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<Name>} if {@code name} is present.
     * If {@code name} is not present, {@code Optional<Name.UNSPECIFIED>} will be returned
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Name> parseRelationshipName(Optional<String> name) throws IllegalValueException {
        requireNonNull(name);
        return name.isPresent() ? Optional.of(new Name(name.get())) : Optional.of(Name.UNSPECIFIED);
    }

    /**
     * Parses a {@code Optional<String> phone} into an {@code Optional<Phone>} if {@code phone} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Phone> parsePhone(Optional<String> phone) throws IllegalValueException {
        requireNonNull(phone);
        return phone.isPresent() ? Optional.of(new Phone(phone.get())) : Optional.empty();
    }
    //@@author TanYikai
    /**
     * Parses a {@code Optional<String> phone} into an {@code Optional<Phone>} if {@code phone} is present.
     * If {@code phone} is not present, {@code String Unspecified phone number} is given
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Phone> parseAddPhone(Optional<String> phone) throws IllegalValueException {
        requireNonNull(phone);
        return phone.isPresent() ? Optional.of(new Phone(phone.get())) : Optional.of(Phone.UNSPECIFIED);
    }
    //@@author
    /**
     * Parses a {@code Optional<String> address} into an {@code Optional<Address>} if {@code address} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Address> parseAddress(Optional<String> address) throws IllegalValueException {
        requireNonNull(address);
        return address.isPresent() ? Optional.of(new Address(address.get())) : Optional.empty();
    }
    //@@author TanYikai
    /**
     * Parses a {@code Optional<String> address} into an {@code Optional<Address>} if {@code address} is present.
     * If {@code address} is not present, {@code String Unspecified address} is given
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Address> parseAddAddress(Optional<String> address) throws IllegalValueException {
        requireNonNull(address);
        return address.isPresent() ? Optional.of(new Address(address.get())) : Optional.of(Address.UNSPECIFIED);
    }
    //@@author
    /**
     * Parses a {@code Optional<String> email} into an {@code Optional<Email>} if {@code email} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Email> parseEmail(Optional<String> email) throws IllegalValueException {
        requireNonNull(email);
        return email.isPresent() ? Optional.of(new Email(email.get())) : Optional.empty();
    }
    //@@author TanYikai
    /**
     * Parses a {@code Optional<String> email} into an {@code Optional<Email>} if {@code email} is present.
     * If {@code email} is not present, {@code String Unspecified email} is given
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Email> parseAddEmail(Optional<String> email) throws IllegalValueException {
        requireNonNull(email);
        return email.isPresent() ? Optional.of(new Email(email.get())) : Optional.of(Email.UNSPECIFIED);
    }

    /**
     * Parses a {@code Optional<String> remark} into an {@code Optional<Remark>} if {@code remark} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Remark> parseRemark(Optional<String> remark) throws IllegalValueException {
        requireNonNull(remark);
        return remark.isPresent() ? Optional.of(new Remark(remark.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> remark} into an {@code Optional<Remark>} if {@code remark} is present.
     * If {@code remark} is not present, {@code String Unspecified remark} is given
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Remark> parseAddRemark(Optional<String> remark) throws IllegalValueException {
        requireNonNull(remark);
        return remark.isPresent() ? Optional.of(new Remark(remark.get())) : Optional.of(Remark.UNSPECIFIED);
    }
    //@@author
    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws IllegalValueException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a String into a RelationshipDirection
     */
    public static RelationshipDirection parseDirection(String direction) throws IllegalValueException {
        requireNonNull(direction);
        if (direction.compareToIgnoreCase("directed") == 0) {
            return RelationshipDirection.DIRECTED;
        } else if (direction.compareToIgnoreCase("undirected") == 0)  {
            return RelationshipDirection.UNDIRECTED;
        } else {
            throw new IllegalValueException(MESSAGE_INVALID_DIRECTION);
        }
    }

    /**
     * Parses a {@code Optional<String> estimate} into an {@code Optional<ConfidenceEstimate>}
     * if {@code estimate} is present.
     * If {@code estimate} is not present, {@code Optional<ConfidenceEstimate.Unspecified>} is returned
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<ConfidenceEstimate> parseConfidenceEstimate(Optional<String> estimate)
            throws IllegalValueException {
        requireNonNull(estimate);
        return estimate.isPresent() ? Optional.of(new ConfidenceEstimate(estimate.get()))
                : Optional.of(ConfidenceEstimate.UNSPECIFIED);

    }
}
