let initialData = [];

const fetchInitialData = (input) => {
    const trimmedInput = input.trim();

    if (trimmedInput.length >= 3) {
        $.ajax({
            type: 'GET',
            url: '/conference-search?title=' + trimmedInput,
            success: function (data) {
                displaySuggestions(data);
            }
        });
    } else {
        displaySuggestions([]);
    }
}

const search = (input) => {
    const trimmedInput = input.trim();

    if (initialData.length === 0) {
        fetchInitialData(trimmedInput);

    } else {
        const filteredData = initialData.filter(item =>
            item.title.toLowerCase().includes(trimmedInput.toLowerCase())
        );
        displaySuggestions(filteredData);
    }
}

const displaySuggestions = (conferences) => {
    const conferenceList = $('#conferenceList');
    conferenceList.empty();

    if (conferences.length > 0) {
        const list = $('<ul class="list-group"></ul>');

        conferences.forEach(function (conference) {
            const listItem = $('<li class="list-group-item">' + conference.title + '</li>');

            listItem.click(function () {
                $('#conference').val(conference.title);
                $('#conferenceValue').val(conference.id);
                conferenceList.empty();
            });

            list.append(listItem);
        });

        conferenceList.append(list);
    }
};

const toggleCommentButton = (input) => {
    const commentButton = document.getElementById('commentButton');
    const cancelButton = document.getElementById('cancelButton');

    commentButton.disabled = (input.trim() === '');
    cancelButton.disabled = (input.trim() === '');
}

const cancelComment = () => {
    document.getElementById('commentText').value = '';
    document.getElementById('commentButton').disabled = true;
    document.getElementById('cancelButton').disabled = true;
}

const showRatingForm = () => {
    document.getElementById("currentRating").style.display = "none";
    document.getElementById("ratingSelectForm").style.display = "block";
}

const cancelUpdateRating = () => {
    document.getElementById("currentRating").style.display = "block";
    document.getElementById("ratingSelectForm").style.display = "none";
}

$(document).ready(function() {
    $('#multiple-checkboxes').multiselect({
        includeSelectAllOption: true,
    });
});

$(document).ready(function() {
    $('#multiple-checkboxes-tags').multiselect({
        includeSelectAllOption: true,
    });
});