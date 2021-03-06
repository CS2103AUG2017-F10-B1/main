package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.commands.RemoveTagCommand.MESSAGE_TAG_NOT_FOUND;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.person.exceptions.TagNotFoundException;

/**
 * A list of tags that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Tag#equals(Object)
 */
public class UniqueTagList implements Iterable<Tag> {

    private final ObservableList<Tag> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty TagList.
     */
    public UniqueTagList() {}

    /**
     * Creates a UniqueTagList using given tags.
     * Enforces no nulls.
     */
    public UniqueTagList(Set<Tag> tags) {
        requireAllNonNull(tags);
        internalList.addAll(tags);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all tags in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Tag> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the Tags in this list with those in the argument tag list.
     */
    public void setTags(Set<Tag> tags) {
        requireAllNonNull(tags);
        internalList.setAll(tags);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Ensures every tag in the argument list exists in this object.
     */
    public void mergeFrom(UniqueTagList from) {
        final Set<Tag> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(tag -> !alreadyInside.contains(tag))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent Tag as the given argument.
     */
    public boolean contains(Tag toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    //@@author wenmogu
    /**
     * If the tag name is valid and the tag present in the list.
     * @param tagName
     * @return the index of the tag in the list
     * @throws IllegalValueException
     */
    public int indexOfTagWithName(String tagName) throws IllegalValueException {
        requireNonNull(tagName);
        Tag temp = new Tag(tagName);
        return internalList.indexOf(temp);
    }

    //@@author
    /**
     * Adds a Tag to the list.
     *
     * @throws DuplicateTagException if the Tag to add is a duplicate of an existing Tag in the list.
     */
    public void add(Tag toAdd) throws DuplicateTagException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTagException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    //@@author wenmogu
    /**
     * Remove a Tag from the list. The tag is identified by its name.
     *
     * throws TagNotFoundException if the Tag to remove is not found in the list.
     * throws IllegalValueException if the Tag name input is invalid.
     */
    public Tag removeTag(String tagGettingRemoved) throws TagNotFoundException, IllegalValueException {
        requireNonNull(tagGettingRemoved);
        int tagIndexInList = indexOfTagWithName(tagGettingRemoved);
        if (tagIndexInList == -1) {
            throw new TagNotFoundException(MESSAGE_TAG_NOT_FOUND);
        } else {
            return internalList.remove(tagIndexInList);
        }
    }

    /**
     * Remove a tag from the list.
     * @param tagGettingRemoved
     */
    public boolean removeTag(Tag tagGettingRemoved) {
        requireNonNull(tagGettingRemoved);
        return internalList.remove(tagGettingRemoved);
    }

    //@@author
    @Override
    public Iterator<Tag> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Tag> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniqueTagList // instanceof handles nulls
                        && this.internalList.equals(((UniqueTagList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueTagList other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        assert CollectionUtil.elementsAreUnique(other.internalList);
        return this == other || new HashSet<>(this.internalList).equals(new HashSet<>(other.internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateTagException extends DuplicateDataException {
        protected DuplicateTagException() {
            super("Operation would result in duplicate tags");
        }
    }

}
